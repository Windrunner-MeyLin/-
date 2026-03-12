package UI;

import bean.User;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RegisterInterface extends JFrame {
    private static ArrayList<User> users;

    // 颜色定义 (与登录界面保持一致)
    private static final Color PRIMARY_COLOR = new Color(42, 90, 64); // 深军绿
    private static final Color PRIMARY_HOVER = new Color(50, 105, 75);
    private static final Color BG_COLOR = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);
    private static final Color ERROR_COLOR = new Color(180, 50, 50);

    private JTextField accountField;
    private JPasswordField passField;
    private JPasswordField confirmPassField;
    private JButton registerBtn;
    private JButton backLabel; // 返回登录的链接

    public RegisterInterface(ArrayList<User> users) {
        this.users = users;
        initUI();
    }


    private void initUI() {
        // 1. 基础窗口设置
        setTitle("人员信息采集与注册系统 - 总参谋部人事管理系统");
        setSize(420, 480); // 高度略增以容纳确认密码框
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭只销毁当前窗口，不退出程序
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_COLOR);

        // 2. 主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // 3. 标题部分
        JLabel titleLabel = new JLabel("新用户注册", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 5, 0));

        JLabel subTitleLabel = new JLabel("请填写您的账号信息以完成注册", SwingConstants.CENTER);
        subTitleLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        subTitleLabel.setForeground(TEXT_COLOR);
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitleLabel.setBorder(new EmptyBorder(0, 0, 25, 0));

        // 4. 输入区域面板
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(BG_COLOR);
        inputPanel.setMaximumSize(new Dimension(300, 200));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // --- 账号行 ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel accLabel = new JLabel("账 号：");
        accLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        accLabel.setForeground(TEXT_COLOR);
        inputPanel.add(accLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        accountField = createStyledTextField();
        inputPanel.add(accountField, gbc);

        // --- 密码行 ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel passLabel = new JLabel("密 码：");
        passLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        passLabel.setForeground(TEXT_COLOR);
        inputPanel.add(passLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        passField = createStyledPasswordField();
        inputPanel.add(passField, gbc);

        // --- 确认密码行 (新增，提升体验) ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel confirmLabel = new JLabel("确认密码：");
        confirmLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        confirmLabel.setForeground(TEXT_COLOR);
        inputPanel.add(confirmLabel, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        confirmPassField = createStyledPasswordField();
        inputPanel.add(confirmPassField, gbc);

        // 5. 注册按钮
        registerBtn = new JButton("立 即 注 册");
        registerBtn.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        registerBtn.setForeground(Color.BLACK);
        registerBtn.setBackground(PRIMARY_COLOR);
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setMaximumSize(new Dimension(300, 50));
        registerBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(20, 50, 35), 1),
                new EmptyBorder(12, 0, 12, 0)
        ));

        // 按钮鼠标效果
        registerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerBtn.setBackground(PRIMARY_HOVER);
                registerBtn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(30, 60, 45), 1),
                        new EmptyBorder(12, 0, 12, 0)
                ));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                registerBtn.setBackground(PRIMARY_COLOR);
                registerBtn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(20, 50, 35), 1),
                        new EmptyBorder(12, 0, 12, 0)
                ));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                registerBtn.setBackground(new Color(30, 70, 50));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (registerBtn.getBounds().contains(e.getPoint())) {
                    mouseEntered(e);
                } else {
                    mouseExited(e);
                }
            }
        });

        // 注册事件
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegister();
            }
        });

        // 6. 返回登录链接 用Jbutton实现
        JButton backLabel = new JButton("返回登录");
        backLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        backLabel.setForeground(TEXT_COLOR);
        backLabel.setBorder(null);
        backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backLabel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginInterface();
                dispose();
            }
        });


        // 7. 组装
        mainPanel.add(titleLabel);
        mainPanel.add(subTitleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(registerBtn);
        mainPanel.add(backLabel);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);

        // 支持回车键注册
        KeyAdapter enterAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performRegister();
                }
            }
        };
        accountField.addKeyListener(enterAdapter);
        passField.addKeyListener(enterAdapter);
        confirmPassField.addKeyListener(enterAdapter);

        setVisible(true);
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

    private void performRegister() {
        String account = accountField.getText().trim();
        String password = new String(passField.getPassword());
        String confirmPassword = new String(confirmPassField.getPassword());

        // 1. 非空验证
        if (account.isEmpty()) {
            showError("请输入账号！");
            accountField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            showError("请输入密码！");
            passField.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            showError("请确认密码！");
            confirmPassField.requestFocus();
            return;
        }

        // 2. 密码一致性验证
        if (!password.equals(confirmPassword)) {
            showError("两次输入的密码不一致，请重新输入！");
            confirmPassField.setText("");
            confirmPassField.requestFocus();
            // 清空密码框以示警示
            passField.setText("");
            confirmPassField.setBorder(new CompoundBorder(
                    new LineBorder(ERROR_COLOR, 2), // 红色边框提示错误
                    new EmptyBorder(5, 10, 5, 10)
            ));
            return;
        } else {
            // 恢复边框
            resetFieldStyle(confirmPassField);
            resetFieldStyle(passField);
        }
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getAccount().equals(account))
            {
                showError("该账号已存在！");
                accountField.requestFocus();
                return;
            }
        }

        // 3. 模拟注册提交
        registerBtn.setEnabled(false);
        registerBtn.setText("注册中...");
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        users.add(user);

        JOptionPane.showMessageDialog(RegisterInterface.this,
                "注册成功！\n账号：" + account + "\n即将返回登录界面。",
                "注册成功",
                JOptionPane.INFORMATION_MESSAGE);

        // 恢复按钮状态
        registerBtn.setEnabled(true);
        registerBtn.setText("立 即 注 册");

        // 清空输入框
        accountField.setText("");
        passField.setText("");
        confirmPassField.setText("");

        // 返回登录界面
        new LoginInterface();
        dispose();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "输入错误", JOptionPane.ERROR_MESSAGE);
    }

    private void resetFieldStyle(JComponent field) {
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
    }

    }
