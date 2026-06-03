package com.krysteltm.UTIL;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.prefs.Preferences;

public final class Theme {

    public static final Color PRIMARY        = new Color(0xA0, 0x20, 0x20);
    public static final Color PRIMARY_DARK   = new Color(0x7A, 0x18, 0x18);
    public static final Color PRIMARY_HOVER  = new Color(0xB3, 0x1B, 0x1B);
    public static final Color ACCENT         = new Color(0x00, 0x83, 0x8F);
    public static final Color ACCENT_DARK    = new Color(0x00, 0x60, 0x64);

    public static final Color BACKGROUND     = new Color(0xF7, 0xF7, 0xFA);
    public static final Color SURFACE        = Color.WHITE;
    public static final Color SURFACE_ALT    = new Color(0xFA, 0xFA, 0xFC);

    public static final Color TEXT_PRIMARY   = new Color(0x1F, 0x1F, 0x1F);
    public static final Color TEXT_SECONDARY = new Color(0x6B, 0x6B, 0x6B);
    public static final Color BORDER         = new Color(0xE5, 0xE7, 0xEB);

    public static final Color SUCCESS = new Color(0x2E, 0x7D, 0x32);
    public static final Color WARNING = new Color(0xED, 0x6C, 0x02);
    public static final Color ERROR   = new Color(0xC6, 0x28, 0x28);
    public static final Color INFO    = new Color(0x02, 0x77, 0xBD);
    public static final Color NEUTRAL = new Color(0x90, 0x90, 0x90);

    private static final String PREF_KEY_DARK = "theme.dark";
    private static boolean dark = false;

    private Theme() {}

    public static void install() {
        Preferences prefs = Preferences.userNodeForPackage(Theme.class);
        dark = prefs.getBoolean(PREF_KEY_DARK, false);

        Font base = pickBaseFont();
        UIManager.put("defaultFont", new FontUIResource(base));

        if (dark) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }

        applyCommonDefaults();
    }

    public static void toggleDarkMode() {
        dark = !dark;
        Preferences.userNodeForPackage(Theme.class).putBoolean(PREF_KEY_DARK, dark);
        try {
            if (dark) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
            applyCommonDefaults();
            FlatLaf.updateUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isDark() {
        return dark;
    }

    private static Font pickBaseFont() {
        String[] preferred = {"Segoe UI", "Inter", "SF Pro Text", "Roboto", "Arial"};
        String[] available = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String name : preferred) {
            for (String a : available) {
                if (a.equalsIgnoreCase(name)) {
                    return new Font(name, Font.PLAIN, 14);
                }
            }
        }
        return new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    }

    private static void applyCommonDefaults() {
        UIManager.put("Button.arc", 16);
        UIManager.put("Component.arc", 12);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("ProgressBar.arc", 12);

        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 1);
        UIManager.put("Component.focusColor", PRIMARY);
        UIManager.put("Component.accentColor", PRIMARY);
        if (!dark) {
            UIManager.put("Component.borderColor", BORDER);
        }

        UIManager.put("Button.default.background", PRIMARY);
        UIManager.put("Button.default.foreground", Color.WHITE);
        UIManager.put("Button.default.hoverBackground", PRIMARY_HOVER);
        UIManager.put("Button.default.pressedBackground", PRIMARY_DARK);

        UIManager.put("Table.showHorizontalLines", Boolean.FALSE);
        UIManager.put("Table.showVerticalLines", Boolean.FALSE);
        UIManager.put("Table.intercellSpacing", new Dimension(0, 0));
        UIManager.put("Table.rowHeight", 34);
        if (!dark) {
            UIManager.put("Table.alternateRowColor", SURFACE_ALT);
        }
        UIManager.put("TableHeader.background", PRIMARY);
        UIManager.put("TableHeader.foreground", Color.WHITE);
        UIManager.put("TableHeader.height", 36);
        UIManager.put("TableHeader.font", deriveFont(Font.BOLD, 13f));

        UIManager.put("TitlePane.background", Color.WHITE);
        UIManager.put("TitlePane.foreground", TEXT_PRIMARY);
        UIManager.put("TitlePane.inactiveBackground", new Color(0xF5, 0xF5, 0xF7));
        UIManager.put("TitlePane.inactiveForeground", TEXT_SECONDARY);
        UIManager.put("TitlePane.unifiedBackground", Boolean.TRUE);
        UIManager.put("TitlePane.buttonSymbolColor", TEXT_PRIMARY);
        UIManager.put("TitlePane.buttonForeground", TEXT_PRIMARY);
        UIManager.put("TitlePane.buttonHoverBackground", new Color(0xE5, 0xE5, 0xE5));
        UIManager.put("TitlePane.buttonPressedBackground", new Color(0xCC, 0xCC, 0xCC));
        UIManager.put("TitlePane.closeHoverBackground", new Color(0xC4, 0x2B, 0x1C));
        UIManager.put("TitlePane.closePressedBackground", new Color(0x88, 0x14, 0x0A));
        UIManager.put("TitlePane.closeHoverForeground", Color.WHITE);
        UIManager.put("TitlePane.closePressedForeground", Color.WHITE);
        UIManager.put("TitlePane.embeddedForeground", TEXT_PRIMARY);

        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.width", 10);
        UIManager.put("ScrollBar.showButtons", Boolean.FALSE);

        UIManager.put("TextField.placeholderForeground", TEXT_SECONDARY);
        UIManager.put("PasswordField.placeholderForeground", TEXT_SECONDARY);

        UIManager.put("TabbedPane.tabHeight", 36);
        UIManager.put("TabbedPane.selectedBackground", SURFACE);
        UIManager.put("MenuItem.selectionType", "underline");
    }

    private static Font deriveFont(int style, float size) {
        Font base = UIManager.getFont("defaultFont");
        if (base == null) base = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        return base.deriveFont(style, size);
    }

    public static void applyHeadingFont(JLabel lbl, float size) {
        lbl.setFont(deriveFont(Font.BOLD, size));
    }

    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        );
    }

    public static Border cardBorderPadded(int top, int left, int bottom, int right) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(top, left, bottom, right)
        );
    }

    public static JPanel gradientPanel(Color top, Color bottom) {
        return new GradientPanel(top, bottom);
    }

    public static void installGradient(JPanel panel, Color top, Color bottom) {
        panel.setOpaque(true);
        panel.setUI(new javax.swing.plaf.basic.BasicPanelUI() {
            @Override
            public void update(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = c.getWidth();
                int h = c.getHeight();
                g2.setPaint(new GradientPaint(0, 0, top, 0, h, bottom));
                g2.fillRect(0, 0, w, h);
                g2.dispose();
                paint(g, c);
            }
        });
    }

    public static void shadowedButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.putClientProperty("JButton.buttonType", "roundRect");
        btn.putClientProperty("FlatLaf.style", "arc: 999; borderWidth: 0");
    }

    public static void asTile(JButton btn, Icon icon, Color accent) {
        btn.setIcon(icon);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setIconTextGap(12);
        btn.setFocusPainted(false);
        btn.setBackground(SURFACE);
        btn.setForeground(TEXT_PRIMARY);
        btn.setFont(deriveFont(Font.BOLD, 14f));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(20, 16, 20, 16)
        ));
        btn.putClientProperty("JButton.buttonType", "roundRect");
        if (accent != null) {
            btn.putClientProperty("JComponent.outline", accent);
        }
    }

    public static TableCellRenderer statusBadgeRenderer() {
        return new StatusBadgeRenderer();
    }

    public static FlatSVGIcon icon(String name, int size, Color color) {
        FlatSVGIcon ic = new FlatSVGIcon("icons/" + name + ".svg", size, size);
        if (color != null) {
            ic.setColorFilter(new FlatSVGIcon.ColorFilter(c -> color));
        }
        return ic;
    }

    public static FlatSVGIcon icon(String name, int size) {
        return icon(name, size, null);
    }

    public static Image appIconImage(int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, PRIMARY, size, size, PRIMARY_DARK));
        g2.fillRoundRect(0, 0, size, size, size / 4, size / 4);
        g2.setColor(Color.WHITE);
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, (int) (size * 0.5));
        g2.setFont(f);
        FontMetrics fm = g2.getFontMetrics();
        String text = "R";
        int tx = (size - fm.stringWidth(text)) / 2;
        int ty = (size - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(text, tx, ty);
        g2.dispose();
        return img;
    }

    public static void applyAppIcon(JFrame frame) {
        frame.setIconImage(appIconImage(64));
    }

    public static void styleHeaderPanel(JComponent panel) {
        panel.setBackground(PRIMARY);
    }

    private static class GradientPanel extends JPanel {
        private final Color top;
        private final Color bottom;

        GradientPanel(Color top, Color bottom) {
            this.top = top;
            this.bottom = bottom;
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            g2.setPaint(new GradientPaint(0, 0, top, 0, h, bottom));
            g2.fillRect(0, 0, w, h);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class StatusBadgeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JLabel base = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            String text = value == null ? "" : value.toString().trim().toUpperCase();
            BadgePanel badge = new BadgePanel(text, colorFor(text));
            if (isSelected) {
                badge.setBackground(table.getSelectionBackground());
            } else {
                badge.setBackground(table.getBackground());
            }
            return badge;
        }

        private Color colorFor(String status) {
            switch (status) {
                case "APROBADA":
                case "APROBADO":
                case "ACEPTADA":
                    return Theme.SUCCESS;
                case "RECHAZADA":
                case "RECHAZADO":
                    return Theme.ERROR;
                case "PENDIENTE":
                    return Theme.WARNING;
                case "REVISION":
                case "REVISIÓN":
                case "EN REVISION":
                case "EN REVISIÓN":
                    return Theme.INFO;
                default:
                    return Theme.NEUTRAL;
            }
        }
    }

    private static class BadgePanel extends JPanel {
        private final String text;
        private final Color color;

        BadgePanel(String text, Color color) {
            this.text = text;
            this.color = color;
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 6));
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            FontMetrics fm = g2.getFontMetrics(getFont().deriveFont(Font.BOLD, 11f));
            int padH = 14;
            int padV = 6;
            int textW = fm.stringWidth(text);
            int textH = fm.getAscent();
            int badgeW = textW + padH * 2;
            int badgeH = textH + padV * 2;

            int x = (getWidth() - badgeW) / 2;
            int y = (getHeight() - badgeH) / 2;

            g2.setColor(color);
            g2.fillRoundRect(x, y, badgeW, badgeH, badgeH, badgeH);

            g2.setFont(getFont().deriveFont(Font.BOLD, 11f));
            g2.setColor(Color.WHITE);
            g2.drawString(text, x + padH, y + padV + textH - 2);
            g2.dispose();
        }
    }
}
