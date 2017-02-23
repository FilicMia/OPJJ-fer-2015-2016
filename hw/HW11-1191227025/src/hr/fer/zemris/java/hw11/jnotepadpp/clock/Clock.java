package hr.fer.zemris.java.hw11.jnotepadpp.clock;

import javax.swing.*;

import java.util.*;
import java.util.Timer;
import java.text.SimpleDateFormat;

/**
 * Implementing the {@code Clock} of format 2015/05/15 15:35:24..
 * 
 * @author mia
 *
 */
public class Clock {
	/**
	 * Label in which the time will be displayed.
	 */
	private final JLabel clockLabel;
	/**
	 * Format of the clock. Seconds are missing. But it will be displayed.
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	/**
	 * Current seconds.
	 */
	private int currentSecond;

	/** Current time. */
	private Calendar calendar;

	/** Constructor. */
	public Clock() {
		clockLabel = new JLabel();
		clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	/**
	 * Resets the clock.
	 */
	private void reset() {
		calendar = Calendar.getInstance();
		currentSecond = calendar.get(Calendar.SECOND);
	}

	/**
	 * Starts the clock.
	 */
	public void start() {
		reset();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (currentSecond == 60) {
					reset();
				}
				clockLabel.setText(String.format("%s:%02d",
						sdf.format(calendar.getTime()), currentSecond));
				currentSecond++;
			}
		}, 0, 1000);
	}

	/**
	 * @return the {@link JLabel} representation of the clock.
	 */
	public JLabel get() {
		return clockLabel;
	}
}
