package com.company;

/**
 * Created by Sahil Ashar on 5/25/2014.
 * Version 1.0.0
 */
import javax.sound.midi.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * This class is a Swing component that can load and play a sound clip,
 * displaying progress and controls.  The main( ) method is a test program.
 * This component can play sampled audio or MIDI files, but handles them
 * differently. For sampled audio, time is reported in microseconds, tracked in
 * milliseconds and displayed in seconds and tenths of seconds. For midi
 * files time is reported, tracked, and displayed in MIDI "ticks".
 * This program does no transcoding, so it can only play sound files that use
 * the PCM encoding.
 */
public class Song extends JComponent implements Serializable
{
    File file;

    float sampleRate;

    boolean midi;            // Are we playing a midi file or a sampled one?
    Sequence sequence;       // The contents of a MIDI file
    Sequencer sequencer;     // We play MIDI Sequences with a Sequencer
    Clip clip;               // Contents of a sampled audio file
    boolean playing = false; // whether the sound is currently playing

    // Length and position of the sound are measured in milliseconds for
    // sampled sounds and MIDI "ticks" for MIDI sounds
    int audioLength;         // Length of the sound.
    int audioPosition = 0;   // Current position within the sound

    // The following fields are for the GUI
    JButton play;             // The Play/Stop button
    JSlider progress;         // Shows and sets current position in sound
    JLabel time;              // Displays audioPosition as a number
    Timer timer;              // Updates slider every 100 milliseconds

    // The main method just creates a Song in a Frame and displays it
    /*public static void main(String args[])
        throws IOException,
               UnsupportedAudioFileException,
               LineUnavailableException,
               MidiUnavailableException,
               InvalidMidiDataException
    {
        Song player;

        File file = new File("Song.m4a");   // This is the file we'll be playing

        // Create a Song object to play the sound.
        player = new Song(file);

        // Put it in a window and play it
        JFrame f = new JFrame("Song");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane( ).add(player, "Center");
        f.pack( );
        f.setVisible(true);
    }*/

    public Song()
    {
    }

    // Create a Song component for the specified file.
    public Song(File f)
            throws IOException,
            UnsupportedAudioFileException,
            LineUnavailableException,
            MidiUnavailableException,
            InvalidMidiDataException
    {
        boolean isMidi;
        try {
            // We discard the return value of this method; we just need to know
            // whether it returns successfully or throws an exception
            MidiSystem.getMidiFileFormat(f);
            isMidi = true;
        }
        catch(InvalidMidiDataException e) {
            isMidi = false;
        }

        file = f;
        if (isMidi) {     // The file is a MIDI file
            midi = true;
            // First, get a Sequencer to play sequences of MIDI events
            // That is, to send events to a Synthesizer at the right time.
            sequencer = MidiSystem.getSequencer( );  // Used to play sequences
            sequencer.open( );                       // Turn it on.

            // Get a Synthesizer for the Sequencer to send notes to
            Synthesizer synth = MidiSystem.getSynthesizer( );
            synth.open( );  // acquire whatever resources it needs

            // The Sequencer obtained above may be connected to a Synthesizer
            // by default, or it may not.  Therefore, we explicitly connect it.
            Transmitter transmitter = sequencer.getTransmitter( );
            Receiver receiver = synth.getReceiver( );
            transmitter.setReceiver(receiver);

            // Read the sequence from the file and tell the sequencer about it
            sequence = MidiSystem.getSequence(f);
            sequencer.setSequence(sequence);
            audioLength = (int)sequence.getTickLength( ); // Get sequence length
        }
        else {            // The file is sampled audio
            midi = false;
            // Getting a Clip object for a file of sampled audio data is kind
            // of cumbersome.  The following lines do what we need.
            AudioInputStream ain = AudioSystem.getAudioInputStream(f);
            try {
                DataLine.Info info =
                        new DataLine.Info(Clip.class,ain.getFormat( ));
                sampleRate = ain.getFormat().getSampleRate();
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(ain);
            }
            finally { // We're done with the input stream.
                ain.close( );
            }
            clip.setLoopPoints(0,-1);
            // Get the clip length in microseconds and convert to milliseconds
            audioLength = (int)(clip.getMicrosecondLength( )/1000);
        }

        // Now create the basic GUI
        play = new JButton("Play");                // Play/stop button
        progress = new JSlider(0, audioLength, 0); // Shows position in sound
        time = new JLabel("0");                    // Shows position as a #

        // When clicked, start or stop playing the sound
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (playing) stop(); else play();
            }
        });

        // Whenever the slider value changes, first update the time label.
        // Next, if we're not already at the new position, skip to it.
        progress.addChangeListener(new ChangeListener( ) {
            public void stateChanged(ChangeEvent e) {
                int value = progress.getValue( );
                // Update the time label
                if (midi) time.setText(value + "");
                else time.setText(value/1000 + "." +
                        (value%1000)/100);
                // If we're not already there, skip there.
                if (value != audioPosition) skip(value);
            }
        });

        // This timer calls the tick( ) method 10 times a second to keep
        // our slider in sync with the music.
        timer = new javax.swing.Timer(100, new ActionListener( ) {
            public void actionPerformed(ActionEvent e) { tick( ); }
        });

        // put those controls in a row
        Box row = Box.createHorizontalBox( );
        row.add(play);
        row.add(progress);
        row.add(time);

        // And add them to this component.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(row);
    }

    public void play( ) {
        if (midi) sequencer.start();
        else
        {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        timer.start( );
        play.setText("Stop");
        playing = true;
    }

    public void play(int s, int e) {
        //called when you want to loop a song
        if (midi) sequencer.start();
        else
        {
            int start = (int)(s*sampleRate/1000);
            int end = (int)(e*sampleRate/1000);
            if(end<0)
                clip.setLoopPoints(start,-1);
            else
                clip.setLoopPoints(start,end);
            //clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        timer.start( );
        play.setText("Stop");
        playing = true;
    }

    public void stop( ) {
        timer.stop( );
        if (midi) sequencer.stop( );
        else clip.stop( );
        play.setText("Play");
        playing = false;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    /** Stop playing the sound and reset the position to 0 */
    public void reset( ) {
        stop( );
        if (midi) sequencer.setTickPosition(0);
        else clip.setMicrosecondPosition(0);
        audioPosition = 0;
        progress.setValue(0);
    }

    public void skip(int position) { // Called when user drags the slider
        if (position < 0 || position > audioLength) return;
        audioPosition = position;
        if (midi) sequencer.setTickPosition(position);
        else clip.setMicrosecondPosition(position * 1000);
        progress.setValue(position); // in case skip() is called from outside
    }

    /** Return the length of the sound in ms or ticks */
    public int getLength( ) { return audioLength; }

    public int getPosition()
    {
        if (midi && sequencer.isRunning()) {
            return (int)sequencer.getTickPosition();
        }
        else if (!midi && clip.isActive()) {
            return (int)(clip.getMicrosecondPosition()/1000);
        }
        return -1;
    }
    // An internal method that updates the progress bar.
    // The Timer object calls it 10 times a second.
    // If the sound has finished, it resets to the beginning
    void tick( ) {
        if (midi && sequencer.isRunning( )) {
            audioPosition = (int)sequencer.getTickPosition( );
            progress.setValue(audioPosition);
        }
        else if (!midi && clip.isActive( )) {
            audioPosition = (int)(clip.getMicrosecondPosition( )/1000);
            progress.setValue(audioPosition);
        }
        else reset( );
    }

    public long getAudioLength()
    {
        if(midi)
        {
            try{
                return MidiSystem.getMidiFileFormat(file).getMicrosecondLength()/1000;
            } catch(Exception e)
            {
                System.out.println("Something is retarded");
                return 1;
            }
        } else
        {
            return clip.getMicrosecondLength()/1000;
        }
    }
}
