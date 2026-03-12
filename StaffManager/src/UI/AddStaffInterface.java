package UI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import bean.Staff;

public class AddStaffInterface extends JDialog {

    // 颜色常量 (与主界面保持一致)
    private static final Color PRIMARY_COLOR = new Color(42, 90, 64);
    private static final Color PRIMARY_HOVER = new Color(50, 105, 75);
    private static final Color BG_COLOR = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);

    // 输入组件
    private JTextField idField;
    private JTextField nameField;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JTextField phoneField;
    private JTextField jobField;

    // 结果标记
    private Staff createdStaff = null;
    private boolean isConfirmed = false;

    /**
     * 构造函数
     * @param parent 父窗口 (ManagerInterface)
     */
    public AddStaffInterface(JFrame parent) {
        super(parent, "新增人员档案", true); // true 表示模态对话框，阻塞父窗口
        initUI();
    }

    private void initUI() {
        // 1. 基础设置
        setSize(450, 500);
        setLocationRelativeTo(getOwner()); // 相对于父窗口居中
        setResizable(false);
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // 2. 顶部标题
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(BG_COLOR);
        JLabel titleLabel = new JLabel("录入新人员信息", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel);
        headerPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(headerPanel, BorderLayout.NORTH);

        // 3. 中间表单区域
        JPanel formPanel = new JPanel();
        formPanel.setBackground(BG_COLOR);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(10, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- 编号 (ID) ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(createLabel("编 号："), gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        idField = createTextField();
        formPanel.add(idField, gbc);

        // --- 姓名 (Name) ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(createLabel("姓 名："), gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        nameField = createTextField();
        formPanel.add(nameField, gbc);

        // --- 性别 (Sex) - 使用单选框 ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(createLabel("性 别："), gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        JPanel sexPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        sexPanel.setBackground(BG_COLOR);
        ButtonGroup sexGroup = new ButtonGroup();

        maleRadio = new JRadioButton("男");
        femaleRadio = new JRadioButton("女");
        maleRadio.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        femaleRadio.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        maleRadio.setBackground(BG_COLOR);
        femaleRadio.setBackground(BG_COLOR);
        maleRadio.setSelected(true); // 默认选中男

        sexGroup.add(maleRadio);
        sexGroup.add(femaleRadio);
        sexPanel.add(maleRadio);
        sexPanel.add(femaleRadio);
        formPanel.add(sexPanel, gbc);

        // --- 电话 (Phone) ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(createLabel("电 话："), gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        phoneField = createTextField();
        formPanel.add(phoneField, gbc);

        // --- 职务 (Job) ---
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        formPanel.add(createLabel("职 务："), gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        jobField = createTextField();
        formPanel.add(jobField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // 4. 底部按钮区域
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 25, 0));

        JButton confirmBtn = createStyledButton("确 定", PRIMARY_COLOR);
        JButton cancelBtn = createStyledButton("取 消", new Color(100, 100, 100));

        // 确定按钮逻辑
        confirmBtn.addActionListener(e -> {
            if (validateAndCreateStaff()) {
                isConfirmed = true;
                dispose(); // 关闭窗口
            }
        });

        // 取消按钮逻辑
        cancelBtn.addActionListener(e -> {
            isConfirmed = false;
            createdStaff = null;
            dispose();
        });

        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // 支持回车键提交
        KeyAdapter enterAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (validateAndCreateStaff()) {
                        isConfirmed = true;
                        dispose();
                    }
                }
            }
        };
        idField.addKeyListener(enterAdapter);
        nameField.addKeyListener(enterAdapter);
        phoneField.addKeyListener(enterAdapter);
        jobField.addKeyListener(enterAdapter);
    }

    // 辅助方法：创建标签
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    // 辅助方法：创建文本框
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    // 辅助方法：创建风格化按钮
    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 38));
        btn.setBorder(new CompoundBorder(
                new LineBorder(bg.darker(), 1),
                new EmptyBorder(0, 0, 0, 0)
        ));

        final Color normal = bg;
        final Color hover = bg.brighter();
        final Color pressed = bg.darker();

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { if(!bg.equals(new Color(100,100,100))) btn.setBackground(hover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(normal); }
            public void mousePressed(MouseEvent e) { btn.setBackground(pressed); }
            public void mouseReleased(MouseEvent e) {
                if(btn.getBounds().contains(e.getPoint())) btn.setBackground(hover);
                else btn.setBackground(normal);
            }
        });
        return btn;
    }

    // 核心逻辑：验证输入并创建 Staff 对象
    private boolean validateAndCreateStaff() {
        String idStr = idField.getText().trim();
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String job = jobField.getText().trim();
        String sex = maleRadio.isSelected() ? "男" : "女";

        // 1. 非空检查
        if (idStr.isEmpty()) {
            showError("请输入人员编号！");
            idField.requestFocus();
            return false;
        }
        if (name.isEmpty()) {
            showError("请输入姓名！");
            nameField.requestFocus();
            return false;
        }
        if (phone.isEmpty()) {
            showError("请输入联系电话！");
            phoneField.requestFocus();
            return false;
        }
        if (job.isEmpty()) {
            showError("请输入职务！");
            jobField.requestFocus();
            return false;
        }

        // 2. 格式检查 (ID 必须是数字)
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            showError("编号必须是整数数字！");
            idField.requestFocus();
            idField.selectAll();
            return false;
        }

        // 3. 创建对象
        createdStaff = new Staff();
        createdStaff.setId(id);
        createdStaff.setName(name);
        createdStaff.setSex(sex);
        createdStaff.setPhone(phone);
        createdStaff.setJob(job);

        return true;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "输入错误", JOptionPane.ERROR_MESSAGE);
    }

    // 供外部获取结果的方法
    public Staff getCreatedStaff() {
        return createdStaff;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}