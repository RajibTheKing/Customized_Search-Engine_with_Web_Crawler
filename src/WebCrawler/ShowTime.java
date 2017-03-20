package WebCrawler;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Timer;


/**
 *
 * @author rajib_000
 */
public class ShowTime
{
    ShowTime()
    {
        ActionListener timerListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Date date = new Date();
                String time = Main.timeFormat.format(date);
                Main.timeLabel.setText(time);
            }
        };
        Timer timer = new Timer(1000, timerListener);
        timer.setInitialDelay(0);
        timer.start();
        Main.timeLabel.setBounds(775, 0, 300, 50);
        Main.timeLabel.setFont(new Font("arial",Font.BOLD, 15));
        Main.timeLabel.setForeground(Color.ORANGE);
        
    }
}
