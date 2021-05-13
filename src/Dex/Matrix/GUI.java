package Dex.Matrix;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GUI implements ActionListener {
    private final List<String> locale;
    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel(new GridLayout(4,4,2,2));
    private static final Random rand = new Random();
    private final int[][] values = new int[4][4];
    private int Points = 0;

    /**
     * @param localization is list of localized text for the whole program.
     */
    public GUI(List<String> localization)
    {
        locale = localization;
        JButton button = new JButton(locale.get(0));
        frame.setLocationRelativeTo(null);
        button.addActionListener(this);

        Container  container = frame.getContentPane();
        container.add(panel);
        init();
        repaint();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(locale.get(1)+" "+locale.get(2)+": "+ (Points));
        createMenu();
        frame.pack();
        frame.setSize(330,350);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    /**
     * The method that creates the top menu.
     */
    private void createMenu() {
        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu(locale.get(3));

        for (String fileItem : new String [] { locale.get(4), locale.get(5) }) {
            JMenuItem item = new JMenuItem(fileItem);


            if (fileItem.equals(locale.get(4))) {
                KeyStroke HotKey = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
                Action action = new AbstractAction(locale.get(4)) {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        init();
                        repaint();
                        panel.updateUI();
                    }
                };
                action.putValue(Action.ACCELERATOR_KEY, HotKey);
                item.setAction(action);
            }
            else
            {
                KeyStroke HotKey = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
                Action action = new AbstractAction(locale.get(5)) {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                };
                action.putValue(Action.ACCELERATOR_KEY, HotKey);
                item.setAction(action);
            }
            fileMenu.add(item);
        }
        fileMenu.insertSeparator(1);

        menu.add(fileMenu);
        frame.setJMenuBar(menu);
    }

    /**
     * A method that redraws the entire playing field when changes occur on it.
     */
    private void repaint() {
        panel.removeAll();
        panel.validate();
        for (int i = 0; i<4;i++ )
        {
            for (int j = 0; j<4;j++ ) {
                JButton button = new JButton(String.valueOf(values[i][j]));
                if (values[i][j] == 0)
                {
                    button.setVisible(false);
                }
                if (button.getText().equalsIgnoreCase(String.valueOf((i*4)+(j+1))))
                {
                    float[] green = Color.RGBtoHSB(43, 145, 34, null);
                    button.setBackground(Color.getHSBColor(green[0], green[1], green[2]));
                }
                if (!checkWin()) {
                    button.addActionListener(this);
                }
                panel.add(button);

            }
        }
        panel.validate();

    }

    /**
     * A method that clears and re-initializes all game variables.
     */
    private void init() {
        Points = 0;
        frame.setTitle(locale.get(1)+" "+locale.get(2)+": "+ (Points));
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i<16; i++)
        {
            temp.add(i);
        }
        for(int i = 0; i<4; i++)
        {
            for (int j = 0; j< 4; j++) {
                if (temp.size() != 0) {
                    int value = rand.nextInt(temp.size());
                    values[i][j] = temp.get(value);
                    temp.remove(value);
                }
            }

        }
    }

    /**
     * The method that handles the player clicks on the buttons on the playing field
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int num = Integer.parseInt(((JButton) e.getSource()).getText());
        int x = 0, y = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (values[i][j] == num) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        int x0 = 0, y0 = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (values[i][j] == 0) {
                    x0 = i;
                    y0 = j;
                    break;
                }
            }
        }
        if ((x >= x0 - 1 && x <= x0 + 1 && y == y0) || (y >= y0 - 1 && y <= y0 + 1 && x == x0)) {
            values[x][y] = 0;
            values[x0][y0] = num;
            repaint();
            panel.repaint();
            Points++;
            frame.setTitle(locale.get(1)+" "+locale.get(2)+": "+ (Points));
            if (checkWin())
            {
                JOptionPane.showMessageDialog(frame,locale.get(6),locale.get(7), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * A method that checks the playing field and verifies whether the player has won
     */
    public boolean checkWin() {
        boolean status = true;
        if (values[3][3] != 0)
            return false;
        int counter = 1;
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                if (counter != 16) {
                    if (values[i][j] != counter) {
                        status = false;
                    }
                }
                counter++;
            }
        }
        return status;
    }


}
