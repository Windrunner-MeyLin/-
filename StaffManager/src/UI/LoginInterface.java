package UI;

import bean.User;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoginInterface extends JFrame {
    private static ArrayList<User> users = new ArrayList<>();

    // 颜色定义
    // 登录按钮颜色 (深墨绿)
    private static final Color LOGIN_BG = new Color(42, 90, 64);
    private static final Color LOGIN_HOVER = new Color(50, 105, 75);
    private static final Color LOGIN_PRESSED = new Color(30, 70, 50);

    // 注册按钮颜色 (浅军绿，体现次要操作)
    private static final Color REG_BG = new Color(230, 240, 235);
    private static final Color REG_HOVER = new Color(210, 230, 220);
    private static final Color REG_TEXT = new Color(20, 60, 45);

    private static final Color BG_COLOR = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginBtn;
    private JButton registerBtn; // 新增注册按钮

    private int tag = 0;//标识是否匹配

    public LoginInterface() {
        initUI();
    }

    private void initUI() {
        // 1. 基础窗口设置
        setTitle("总参谋部人事管理系统");
        setSize(420, 430); // 高度增加以容纳新按钮
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_COLOR);

        // 2. 主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // 3. 标题部分
        JLabel titleLabel = new JLabel("欢迎进入总参谋部人事管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
        titleLabel.setForeground(new Color(20, 60, 45));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 5, 0));

        // 4. 提示语部分
        JLabel hintLabel = new JLabel("请输入您的账号和密码：", SwingConstants.CENTER);
        hintLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        hintLabel.setForeground(TEXT_COLOR);
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hintLabel.setBorder(new EmptyBorder(0, 0, 25, 0));

        // 5. 输入区域面板
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(BG_COLOR);
        inputPanel.setMaximumSize(new Dimension(300, 150));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // --- 账号行 ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel userLabel = new JLabel("账 号：");
        userLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        userLabel.setForeground(TEXT_COLOR);
        inputPanel.add(userLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        userField = createStyledTextField();
        inputPanel.add(userField, gbc);

        // --- 密码行 ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel passLabel = new JLabel("密 码：");
        passLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        passLabel.setForeground(TEXT_COLOR);
        inputPanel.add(passLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        passField = createStyledPasswordField();
        inputPanel.add(passField, gbc);

        // 6. 登录按钮
        loginBtn = new JButton("登 录 系 统");
        loginBtn.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        loginBtn.setForeground(Color.BLACK); // 黑色文字
        loginBtn.setBackground(LOGIN_BG);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(300, 50));
        loginBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(20, 50, 35), 1),
                new EmptyBorder(12, 0, 12, 0)
        ));

        // 登录按钮交互
        addMouseEffect(loginBtn, LOGIN_BG, LOGIN_HOVER, LOGIN_PRESSED, new Color(20, 50, 35));
        loginBtn.addActionListener(e -> performLogin());

        // 7. 【新增】注册账号按钮
        registerBtn = new JButton("注 册 账 号");
        registerBtn.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        registerBtn.setForeground(REG_TEXT); // 深绿色文字
        registerBtn.setBackground(REG_BG);   // 浅绿色背景
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setMaximumSize(new Dimension(300, 45));
        registerBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 190), 1),
                new EmptyBorder(10, 0, 10, 0)
        ));

        // 注册按钮交互 (浅色方案)
        addMouseEffect(registerBtn, REG_BG, REG_HOVER, new Color(190, 210, 200), new Color(150, 180, 160));
        registerBtn.addActionListener(e -> performRegister());

        // 8. 组装
        mainPanel.add(titleLabel);
        mainPanel.add(hintLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(25)); // 输入框与按钮间的间距
        mainPanel.add(loginBtn);

        // 【关键】登录按钮和注册按钮之间的间距
        mainPanel.add(Box.createVerticalStrut(10));

        mainPanel.add(registerBtn);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);
        setVisible(true);
    }

    // 辅助方法：统一添加鼠标效果监听
    private void addMouseEffect(JButton btn, Color normal, Color hover, Color pressed, Color borderColor) {
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hover);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor.darker(), 1),
                        new EmptyBorder(12, 0, 12, 0)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(normal);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor, 1),
                        new EmptyBorder(12, 0, 12, 0)
                ));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBackground(pressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (btn.getBounds().contains(e.getPoint())) {
                    mouseEntered(e);
                } else {
                    mouseExited(e);
                }
            }
        });
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        field.setEchoChar('●');
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private void performLogin() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "账号或密码不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        loginBtn.setEnabled(false);
        loginBtn.setText("验证中...");

        for (int i = 0; i < users.size(); i++) {
            if ((users.get(i).getAccount().equals(username)) && (users.get(i).getPassword().equals(password))) {
                tag = 1;
                JOptionPane.showMessageDialog(this, "登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                new ManagerInterface();
                dispose();
            }
        }
        //提示登录失败
        if (tag == 0) {
            JOptionPane.showMessageDialog(this, "登录失败！请检查账号和密码是否正确！", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 模拟注册逻辑
    private void performRegister() {
        // 这里可以跳转到注册界面，目前仅做演示
        JOptionPane.showMessageDialog(this,
                "正在跳转至【人员信息采集与注册系统】...\n",
                "注册提示",
                JOptionPane.INFORMATION_MESSAGE);
        new RegisterInterface(users);
        dispose();
    }

}