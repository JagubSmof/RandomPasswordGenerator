import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;

public class PasswordGen {
    private JPanel panel1;
    private JButton btnGeneratePassword;
    private JRadioButton rdoSpecialChars;
    private JRadioButton rdoNumbersOnly;
    private JComboBox cboCharCount;
    private JTextField txtPassword;
    private JButton btnCopy;

    public static void main(String[] args) {
        JFrame frame = new JFrame("PasswordGen");
        frame.setContentPane(new PasswordGen().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public PasswordGen() {
        btnGeneratePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //validate cboCharCount value - must be above 1
                if (cboCharCount.getSelectedIndex() == -1){
                    JOptionPane.showMessageDialog(new JFrame(), "You must tell me how many characters!","Invalid Input", JOptionPane.ERROR_MESSAGE);
                    throw new IllegalArgumentException("cboCharCount.getSelectedIndex() must be greater than -1");
                }

                //centre the text
                txtPassword.setHorizontalAlignment(SwingConstants.CENTER);

                //decide which alphabet to use
                determineUsedAlphabet();

                //initialise variable to this alphabet
                String alphabet = determineUsedAlphabet();

                //initialise position in alphabet array
                int n = alphabet.length();

                //avoid hardcoding the password
                String password = generatePassword(alphabet, n);
                txtPassword.setText(password);
            }
        });

        rdoNumbersOnly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rdoSpecialChars.setSelected(false);
            }
        });

        rdoSpecialChars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rdoNumbersOnly.setSelected(false);
            }
        });
        btnCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = txtPassword.getText();
                StringSelection stringSelection = new StringSelection(password);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
    }

    public String determineUsedAlphabet() {
        //determine possible password alphabets
        final String normalAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final String specialAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!£$%^&*()_+-={}[]:@~;'#<>?,./";
        final String numerics = "1234567890";

        String usedAlphabet;

        //determine which alphabet will be used
        if (rdoSpecialChars.isSelected()) {
            usedAlphabet = specialAlphabet;
        } else if (rdoNumbersOnly.isSelected()){
            usedAlphabet = numerics;
        } else {
            usedAlphabet = normalAlphabet;
        }

        return usedAlphabet;
    }

    public String generatePassword (String alphabet, int alphabetPos) {
        String password = "";

        //create 'random' object
        Random r = new Random();

        //get values from both combo boxes, store them in variables
        int charCount = cboCharCount.getSelectedIndex() + 1;

        //create password using combo box Constraints
        for (int i = 0; i < charCount; i++){
            password += alphabet.charAt(r.nextInt(alphabetPos));
        }

        return password;
    }
}