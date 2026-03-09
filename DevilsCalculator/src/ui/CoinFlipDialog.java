package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CoinFlipDialog extends JDialog {

    private boolean isHeads;
    private int animFrame = 0;
    private Timer animTimer;
    private JLabel resultLabel;
    private CoinPanel coinPanel;

    public CoinFlipDialog(JFrame parent, boolean isHeads) {
        super(parent, "🪙 Coin Flip!", true);
        this.isHeads = isHeads;
        setSize(320, 320);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        buildUI();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(0, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(15, 5, 5));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                // border glow
                g2.setColor(new Color(180, 20, 20, 120));
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 24, 24);
            }
        };
        root.setOpaque(false);
        root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel title = new JLabel("😈 COIN FLIP", SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 20));
        title.setForeground(new Color(220, 50, 50));

        coinPanel = new CoinPanel();
        coinPanel.setPreferredSize(new Dimension(140, 140));

        resultLabel = new JLabel("Flipping...", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        resultLabel.setForeground(new Color(200, 200, 200));

        JButton closeBtn = new JButton("OK") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isHeads ? new Color(20, 120, 20) : new Color(160, 20, 20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Monospaced", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(), (getWidth()-fm.stringWidth(getText()))/2, (getHeight()+fm.getAscent()-fm.getDescent())/2);
            }
        };
        closeBtn.setPreferredSize(new Dimension(100, 36));
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());
        closeBtn.setVisible(false);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        coinPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(coinPanel);
        centerPanel.add(Box.createVerticalStrut(12));
        centerPanel.add(resultLabel);
        centerPanel.add(Box.createVerticalStrut(14));
        centerPanel.add(closeBtn);

        root.add(title, BorderLayout.NORTH);
        root.add(centerPanel, BorderLayout.CENTER);
        setContentPane(root);

        // Animate coin spin for ~1.2s then reveal
        animTimer = new Timer(60, e -> {
            animFrame++;
            coinPanel.setFrame(animFrame);
            coinPanel.repaint();
            if (animFrame >= 20) {
                animTimer.stop();
                coinPanel.setRevealed(true, isHeads);
                coinPanel.repaint();
                if (isHeads) {
                    resultLabel.setText("✅ HEADS — You're safe!");
                    resultLabel.setForeground(new Color(60, 210, 60));
                } else {
                    resultLabel.setText("💀 TAILS — Chaos incoming!");
                    resultLabel.setForeground(new Color(230, 50, 50));
                }
                closeBtn.setVisible(true);
            }
        });
        animTimer.start();
    }

    // Inner panel to draw animated coin
    static class CoinPanel extends JPanel {
        private int frame = 0;
        private boolean revealed = false;
        private boolean heads = true;

        CoinPanel() { setOpaque(false); }

        void setFrame(int f)  { this.frame = f; }
        void setRevealed(boolean r, boolean h) { this.revealed = r; this.heads = h; }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cx = getWidth() / 2;
            int cy = getHeight() / 2;
            int r = 52;

            if (revealed) {
                // Draw full coin
                Color coinColor = heads ? new Color(255, 210, 40) : new Color(180, 30, 30);
                Color rimColor  = heads ? new Color(200, 150, 10) : new Color(120, 10, 10);
                g2.setColor(rimColor);
                g2.fillOval(cx - r - 3, cy - r - 3, (r + 3) * 2, (r + 3) * 2);
                g2.setColor(coinColor);
                g2.fillOval(cx - r, cy - r, r * 2, r * 2);
                // Symbol
                g2.setFont(new Font("Serif", Font.BOLD, 38));
                g2.setColor(rimColor);
                String sym = heads ? "H" : "T";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(sym, cx - fm.stringWidth(sym) / 2, cy + fm.getAscent() / 2 - 4);
            } else {
                // Spinning animation: squish horizontally
                double phase = (frame % 10) / 10.0;
                double scaleX = Math.abs(Math.cos(phase * Math.PI));
                int rx = (int)(r * scaleX) + 2;
                Color spinColor = new Color(220, 180, 30);
                Color spinRim   = new Color(160, 110, 10);
                g2.setColor(spinRim);
                g2.fillOval(cx - rx - 2, cy - r - 2, (rx + 2) * 2, (r + 2) * 2);
                g2.setColor(spinColor);
                g2.fillOval(cx - rx, cy - r, rx * 2, r * 2);
                // Motion lines
                g2.setColor(new Color(255, 230, 100, 80));
                for (int i = 1; i <= 3; i++) {
                    int off = i * 10;
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawOval(cx - rx - off, cy - r + off/2, (rx + off) * 2, (r - off/2) * 2);
                }
            }
        }
    }

    public static void show(JFrame parent, boolean isHeads) {
        CoinFlipDialog d = new CoinFlipDialog(parent, isHeads);
        d.setVisible(true);
    }
}
