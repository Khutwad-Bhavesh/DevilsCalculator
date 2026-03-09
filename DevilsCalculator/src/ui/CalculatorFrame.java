package ui;

import core.Calculator;
import utils.ChaosEngine;
import utils.ChaosEngine.ChaosType;
import utils.RoastMessages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorFrame extends JFrame {

    // ── colours ──────────────────────────────────────────────
    private static final Color BG          = new Color(10,  4,  4);
    private static final Color PANEL_BG    = new Color(18,  6,  6);
    private static final Color DISPLAY_BG  = new Color( 5,  2,  2);
    private static final Color DISPLAY_FG  = new Color(255, 80, 80);
    private static final Color BTN_NUM     = new Color(35, 12, 12);
    private static final Color BTN_OP      = new Color(60, 15, 15);
    private static final Color BTN_EQUALS  = new Color(160, 20, 20);
    private static final Color BTN_CLEAR   = new Color(100, 10, 10);
    private static final Color BTN_HOVER   = new Color(90, 25, 25);
    private static final Color BTN_TEXT    = new Color(240,180,180);
    private static final Color BORDER_CLR  = new Color(120, 20, 20);

    // ── state ────────────────────────────────────────────────
    private final Calculator calc = new Calculator();
    private String display = "0";
    private boolean operatorJustPressed = false;

    // ── widgets ──────────────────────────────────────────────
    private JLabel displayLabel;
    private JLabel subLabel;   // shows operator / expression hint

    public CalculatorFrame() {
        setTitle("😈 Devil's Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        buildUI();
        pack();
        setLocationRelativeTo(null);
    }

    // ─────────────────────────────────────────────────────────
    //  UI BUILD
    // ─────────────────────────────────────────────────────────
    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(0, 0)) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setOpaque(false);
        root.setBorder(BorderFactory.createLineBorder(BORDER_CLR, 2));

        root.add(buildHeader(),  BorderLayout.NORTH);
        root.add(buildDisplay(), BorderLayout.CENTER);
        root.add(buildButtons(), BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 6));
        p.setBackground(new Color(20, 5, 5));
        JLabel lbl = new JLabel("😈  DEVIL'S CALCULATOR  😈");
        lbl.setFont(new Font("Monospaced", Font.BOLD, 14));
        lbl.setForeground(new Color(200, 40, 40));
        p.add(lbl);
        return p;
    }

    private JPanel buildDisplay() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(DISPLAY_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 2, 0, BORDER_CLR),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));

        subLabel = new JLabel(" ");
        subLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        subLabel.setForeground(new Color(140, 40, 40));
        subLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        displayLabel = new JLabel("0");
        displayLabel.setFont(new Font("Monospaced", Font.BOLD, 36));
        displayLabel.setForeground(DISPLAY_FG);
        displayLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        displayLabel.setPreferredSize(new Dimension(340, 48));
        displayLabel.setMinimumSize(new Dimension(340, 48));
        displayLabel.setMaximumSize(new Dimension(340, 48));

        p.add(subLabel);
        p.add(displayLabel);
        return p;
    }

    private JPanel buildButtons() {
        JPanel p = new JPanel(new GridLayout(5, 4, 4, 4));
        p.setBackground(PANEL_BG);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 14, 10));

        String[][] labels = {
            { "C",   "±",  "%",  "÷" },
            { "7",   "8",  "9",  "×" },
            { "4",   "5",  "6",  "−" },
            { "1",   "2",  "3",  "+" },
            { "0",   ".",  "⌫",  "=" },
        };

        for (String[] row : labels) {
            for (String lbl : row) {
                p.add(makeButton(lbl));
            }
        }
        return p;
    }

    // ─────────────────────────────────────────────────────────
    //  BUTTON FACTORY
    // ─────────────────────────────────────────────────────────
    private JButton makeButton(String text) {
        Color base = switch (text) {
            case "="         -> BTN_EQUALS;
            case "+","−","×","÷" -> BTN_OP;
            case "C","±","%","⌫" -> BTN_CLEAR;
            default          -> BTN_NUM;
        };

        JButton btn = new JButton(text) {
            private boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? BTN_HOVER : base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(BORDER_CLR);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.setColor(BTN_TEXT);
                g2.setFont(new Font("Monospaced", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(text,
                    (getWidth()  - fm.stringWidth(text)) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
            }
        };
        btn.setPreferredSize(new Dimension(82, 60));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> handleButton(text));
        return btn;
    }

    // ─────────────────────────────────────────────────────────
    //  BUTTON HANDLER  (coin flip only on operators & equals)
    // ─────────────────────────────────────────────────────────
    private static final java.util.Set<String> CHAOS_TRIGGERS =
        java.util.Set.of("+", "−", "×", "÷", "=");

    private void handleButton(String text) {
        boolean triggersCoin = CHAOS_TRIGGERS.contains(text);

        if (triggersCoin) {
            boolean heads = ChaosEngine.flipCoin();
            CoinFlipDialog.show(this, heads);
            if (!heads) {
                applyChaos();
                return;
            }
        }

        // ── Normal behaviour ──
        switch (text) {
            case "C"  -> clearAll();
            case "⌫"  -> backspace();
            case "±"  -> negate();
            case "%"  -> percent();
            case "+"  -> setOperator("+");
            case "−"  -> setOperator("-");
            case "×"  -> setOperator("*");
            case "÷"  -> setOperator("/");
            case "="  -> equate();
            case "."  -> appendDot();
            default   -> appendDigit(text);
        }
        refreshDisplay();
    }

    // ─────────────────────────────────────────────────────────
    //  CHAOS APPLICATION
    // ─────────────────────────────────────────────────────────
    private void applyChaos() {
        ChaosType chaos = ChaosEngine.rollChaos();
        switch (chaos) {
            case FULL_RESET -> {
                clearAll();
                refreshDisplay();
                showRoast("💀 FULL RESET", RoastMessages.getResetRoast());
            }
            case DELETE_DIGIT -> {
                backspace();
                refreshDisplay();
                showRoast("✂️ DIGIT DELETED", RoastMessages.getDeleteRoast());
            }
            case TROLL_MESSAGE -> {
                showRoast("🤡 TROLL ALERT", RoastMessages.getTrollRoast());
            }
        }
    }

    private void showRoast(String title, String message) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(new Color(15, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        JLabel icon = new JLabel("😈", SwingConstants.CENTER);
        icon.setFont(new Font("Serif", Font.PLAIN, 40));

        JLabel msg = new JLabel("<html><center>" + message + "</center></html>", SwingConstants.CENTER);
        msg.setFont(new Font("Monospaced", Font.BOLD, 14));
        msg.setForeground(new Color(230, 80, 80));

        panel.add(icon, BorderLayout.NORTH);
        panel.add(msg,  BorderLayout.CENTER);

        UIManager.put("OptionPane.background",        new Color(15, 5, 5));
        UIManager.put("Panel.background",             new Color(15, 5, 5));
        UIManager.put("OptionPane.messageForeground", new Color(230, 80, 80));
        UIManager.put("Button.background",            new Color(100, 15, 15));
        UIManager.put("Button.foreground",            Color.WHITE);

        JOptionPane.showMessageDialog(this, panel, title, JOptionPane.PLAIN_MESSAGE);
    }

    // ─────────────────────────────────────────────────────────
    //  CALCULATOR LOGIC
    // ─────────────────────────────────────────────────────────
    private void clearAll() {
        display = "0";
        calc.reset();
        operatorJustPressed = false;
        subLabel.setText(" ");
    }

    private void backspace() {
        if (display.length() > 1) display = display.substring(0, display.length() - 1);
        else display = "0";
    }

    private void negate() {
        double v = Double.parseDouble(display);
        display = formatNumber(-v);
    }

    private void percent() {
        double v = Double.parseDouble(display);
        display = formatNumber(v / 100.0);
    }

    private void appendDigit(String d) {
        if (operatorJustPressed) { display = d; operatorJustPressed = false; return; }
        if (calc.isFreshResult()) { display = d; calc.setFreshResult(false); return; }
        if (display.equals("0")) display = d;
        else if (display.length() < 14) display += d;
    }

    private void appendDot() {
        if (operatorJustPressed) { display = "0."; operatorJustPressed = false; return; }
        if (!display.contains(".")) display += ".";
    }

    private void setOperator(String op) {
        calc.setFirstOperand(Double.parseDouble(display));
        calc.setOperator(op);
        operatorJustPressed = true;
        calc.setFreshResult(false);
        subLabel.setText(formatNumber(calc.getFirstOperand()) + " " + op);
    }

    private void equate() {
        if (calc.getOperator().isEmpty()) return;
        try {
            double result = calc.evaluate(calc.getFirstOperand(), Double.parseDouble(display), calc.getOperator());
            subLabel.setText(formatNumber(calc.getFirstOperand()) + " " + calc.getOperator() + " " + display + " =");
            display = formatNumber(result);
            calc.reset();
            calc.setFreshResult(true);
        } catch (ArithmeticException ex) {
            display = "lol nice try";
            calc.reset();
        }
    }

    private void refreshDisplay() {
        displayLabel.setText(display);
    }

    private String formatNumber(double v) {
        if (v == Math.floor(v) && !Double.isInfinite(v) && Math.abs(v) < 1e12)
            return String.valueOf((long) v);
        return String.valueOf(v);
    }
}
