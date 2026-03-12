package UI;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import bean.Staff;

// 假设 Staff 类已经在同一包下，如果没有，请将下面的 Staff 类代码也保存为 Staff.java
/*
public class Staff {
    private int id;
    private String name;
    private String sex;
    private String phone;
    private String job;
    // ... (您的 getter/setter 方法)
}
*/

public class ManagerInterface extends JFrame {

    // --- 颜色定义 (保持军事风格) ---
    private static final Color PRIMARY_COLOR = new Color(42, 90, 64);      // 深军绿 (主色)
    private static final Color HEADER_BG = new Color(30, 70, 50);          // 表头背景 (更深)
    private static final Color BG_COLOR = new Color(245, 245, 245);        // 页面背景 (浅灰)
    private static final Color TEXT_COLOR = new Color(60, 60, 60);         // 主要文字
    private static final Color ROW_ALT_COLOR = new Color(240, 248, 245);   // 表格隔行变色 (极浅绿)
    private static final Color SELECTED_COLOR = new Color(200, 230, 210);  // 选中行颜色

    private int tag = 0;  //标志符 判断添加人员时是否重复

    // --- 数据容器 ---
    // 根据您的要求，初始化为空
    private List<Staff> allStaff;
    private JTable staffTable;
    private DefaultTableModel tableModel;

    public ManagerInterface() {
        // 1. 初始化数据容器
        allStaff = new ArrayList<>();

        // 【测试用】如果您想看到效果，可以取消下面几行的注释添加几条假数据
        // 如果保持为空，启动时表格就是空的，符合需求
        /*
        Staff s1 = new Staff(); s1.setId(1001); s1.setName("雷战"); s1.setSex("男"); s1.setPhone("13800138000"); s1.setJob("总参谋长");
        Staff s2 = new Staff(); s2.setId(1002); s2.setName("欧阳云"); s2.setSex("女"); s2.setPhone("13912345678"); s2.setJob("情报分析师");
        allStaff.add(s1); allStaff.add(s2);
        */

        initUI();
    }

    private void initUI() {
        // --- 1. 窗口基础设置 ---
        setTitle("总参谋部人员管理系统 - 控制台");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示
        setResizable(false);
        getContentPane().setBackground(BG_COLOR);

        // --- 2. 主布局 (BorderLayout) ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // --- 3. 顶部标题区域 ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("欢迎来到总参谋部人员管理系统！", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel subLabel = new JLabel("人员档案数据库 | 实时状态监控", SwingConstants.CENTER);
        subLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        subLabel.setForeground(new Color(100, 100, 100));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(subLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 4. 中部表格区域 ---
        // 定义列名 (对应 Staff 类的属性)
        String[] columnNames = {"编号", "姓名", "性别", "联系电话", "职务"};

        // 创建模型，初始行数为0
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 禁止直接在表格中编辑
            }
        };

        staffTable = new JTable(tableModel);

        // 表格样式美化
        staffTable.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        staffTable.setRowHeight(35); // 增加行高，更美观
        staffTable.getTableHeader().setReorderingAllowed(false); // 禁止拖拽列顺序
        staffTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选

        // 表头样式
        JTableHeader header = staffTable.getTableHeader();
        header.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        header.setForeground(Color.WHITE);
        header.setBackground(HEADER_BG);
        header.setBorder(new MatteBorder(0, 0, 2, 0, PRIMARY_COLOR)); // 表头底部加一条深绿线

        // 列宽设置
        staffTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // 编号
        staffTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // 姓名
        staffTable.getColumnModel().getColumn(2).setPreferredWidth(60);  // 性别
        staffTable.getColumnModel().getColumn(3).setPreferredWidth(130); // 电话
        staffTable.getColumnModel().getColumn(4).setPreferredWidth(150); // 职务

        // 自定义单元格渲染器 (实现斑马纹 + 选中效果 + 性别颜色区分)
        staffTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    // 非选中状态：设置隔行变色
                    c.setBackground(row % 2 == 0 ? BG_COLOR : ROW_ALT_COLOR);
                    c.setForeground(TEXT_COLOR);

                    // 特殊逻辑：性别列 (索引2) 如果是"男"显示蓝色，"女"显示红色
                    if (column == 2 && value != null) {
                        String sex = value.toString();
                        if ("男".equals(sex)) c.setForeground(new Color(0, 50, 150));
                        else if ("女".equals(sex)) c.setForeground(new Color(150, 0, 50));
                    }
                } else {
                    // 选中状态
                    c.setBackground(SELECTED_COLOR);
                    c.setForeground(PRIMARY_COLOR);
                }

                // 设置居中对齐 (可选，编号和性别通常居中)
                if (column == 0 || column == 2) {
                    ((JLabel)c).setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    ((JLabel)c).setHorizontalAlignment(SwingConstants.LEFT);
                }

                return c;
            }
        });

        // 放入滚动面板
        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        scrollPane.getViewport().setBackground(BG_COLOR);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- 5. 底部操作按钮区域 ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(BG_COLOR);

        JButton btnAdd = createStyledButton("新增人员");
        JButton btnDelete = createStyledButton("删除档案");
        JButton btnRefresh = createStyledButton("刷新列表");

        // 绑定简单事件 (示例)
        btnRefresh.addActionListener(e -> refreshTableData());

        btnDelete.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请先选择要删除的人员！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "确认删除该人员档案吗？此操作不可恢复。", "确认删除", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                allStaff.remove(selectedRow); // 从 List 移除
                tableModel.removeRow(selectedRow); // 从表格移除
                JOptionPane.showMessageDialog(this, "删除成功！");
            }
        });

        btnAdd.addActionListener(e ->{
            // 1. 创建并显示添加窗口 (传入当前窗口作为父窗口)
            AddStaffInterface addDialog = new AddStaffInterface(ManagerInterface.this);
            addDialog.setVisible(true); // 模态显示，代码会在这里暂停，直到用户关闭对话框

            // 2. 用户关闭后，检查是否点击了“确定”
            if (addDialog.isConfirmed()) {
                // 3. 获取新建的 Staff 对象
                Staff newStaff = addDialog.getCreatedStaff();

                if (newStaff != null) {
                    for(int i=0;i<allStaff.size();i++)
                    {
                        if(allStaff.get(i).getId()==newStaff.getId()) {
                            tag =1;
                            JOptionPane.showMessageDialog(ManagerInterface.this,
                                    "该员工编号已存在！",
                                    "错误",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    // 4. 添加到列表
                    if(tag == 0){
                    allStaff.add(newStaff);}
                    tag = 0;

                    // 5. 刷新表格显示
                    refreshTableData();

                    JOptionPane.showMessageDialog(ManagerInterface.this,
                            "人员 [" + newStaff.getName() + "] 添加成功！",
                            "成功",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } );
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 初始加载数据 (虽然现在是空的，但建立机制)
        refreshTableData();

        setVisible(true);
    }

    // 辅助方法：创建统一风格的按钮
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(PRIMARY_COLOR);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CompoundBorder(
                new LineBorder(new Color(20, 50, 35), 1),
                new EmptyBorder(8, 20, 8, 20)
        ));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(50, 105, 75));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(PRIMARY_COLOR);
            }
            public void mousePressed(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(30, 70, 50));
            }
            public void mouseReleased(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(50, 105, 75));
            }
        });
        return btn;
    }

    // 核心方法：将 allStaff 列表的数据刷新到表格中
    private void refreshTableData() {
        tableModel.setRowCount(0); // 先清空表格
        for (Staff s : allStaff) {
            // 按照列顺序添加数据：id, name, sex, phone, job
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getName(),
                    s.getSex(),
                    s.getPhone(),
                    s.getJob()
            });
        }
    }

    // 供外部调用的方法：添加人员后刷新
    public void addStaffAndRefresh(Staff newStaff) {
        allStaff.add(newStaff);
        refreshTableData();
    }

}