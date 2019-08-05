package Note;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class NotePad {
	public static void main(String[] args) {
		new MyFrame("记事本");
	}

}

class MyFrame extends JFrame {
	private JTextArea tv_area = null;
	private String filename = "";
	private JMenuBar mb = null;// 菜单栏
	private JMenu m_file = null;// 文件菜单
	private JMenu m_help = null;// 帮助菜单
	private JMenu m_edit = null;// 编辑菜单
	private JMenu m_format = null;// 格式菜单
	private JMenuItem item_new = null;// 新建
	private JMenuItem item_open = null;// 打开
	private JMenuItem item_exit = null;// 退出
	private JMenuItem item_save = null;// 保存
	private JMenuItem item_saveas = null;// 另存为
	private JMenuItem item_copy = null;// 复制
	private JMenuItem item_paste = null;// 粘贴
	private JMenuItem item_shear = null;// 剪切
	private JMenuItem item_del = null;// 删除
	private JMenuItem item_selectall = null;// 全选
	private JMenuItem item_date = null;// 日期
	private JCheckBoxMenuItem autoswrap = null;
	private JMenuItem item_about = null;// 关于

	private JScrollPane s_pane = null;// 带滚动条的面板

	public MyFrame(String title) {
		super(title);
		init();// 初始化界面
		registerListener();
	}

	// 初始化界面
	private void init() {
		tv_area = new JTextArea();
		s_pane = new JScrollPane(tv_area);
		s_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Container cpane = this.getContentPane();
		cpane.add(s_pane, BorderLayout.CENTER);
		this.setSize(600, 400);// 设置窗体的大小
		// 初始化菜单
		mb = new JMenuBar();

		m_file = new JMenu("文件");
		item_new = new JMenuItem("新建");
		item_open = new JMenuItem("打开");
		item_save = new JMenuItem("保存");
		item_saveas = new JMenuItem("另存为");
		item_exit = new JMenuItem("退出");
		m_file.add(item_new);
		m_file.addSeparator();
		m_file.add(item_open);
		m_file.addSeparator();
		m_file.add(item_save);
		m_file.addSeparator();
		m_file.add(item_saveas);
		m_file.addSeparator();
		m_file.add(item_exit);
		item_new.setMnemonic('N');
		item_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		item_open.setMnemonic('O');
		item_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
		item_save.setMnemonic('S');
		item_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));

		m_help = new JMenu("帮助");
		item_about = new JMenuItem("关于");
		m_help.add(item_about);

		m_edit = new JMenu("编辑");
		item_copy = new JMenuItem("复制");
		item_paste = new JMenuItem("粘贴");
		item_shear = new JMenuItem("剪切");
		item_del = new JMenuItem("删除");
		item_selectall = new JMenuItem("全选");
		item_date = new JMenuItem("日期");
		m_edit.add(item_shear);
		m_edit.addSeparator();
		m_edit.add(item_copy);
		m_edit.addSeparator();
		m_edit.add(item_paste);
		m_edit.addSeparator();
		m_edit.add(item_del);
		m_edit.addSeparator();
		m_edit.add(item_selectall);
		m_edit.addSeparator();
		m_edit.add(item_date);
		item_copy.setMnemonic('C');
		item_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		item_paste.setMnemonic('V');
		item_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
		item_shear.setMnemonic('X');
		item_shear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
		item_selectall.setMnemonic('A');
		item_selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));

		m_format = new JMenu("格式");
		autoswrap = new JCheckBoxMenuItem("自动换行", false);
		m_format.add(autoswrap);

		mb.add(m_file);
		mb.add(m_edit);
		mb.add(m_format);
		mb.add(m_help);
		// 把菜单栏添加到窗体中
		this.setJMenuBar(mb);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗口的时候退出应用程序
		// 设置窗口居中显示
		// 定义一个工具包
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;// 获得屏幕的宽度
		int screenHeight = screenSize.height;// 获得屏幕的高度
		int windowWidth = this.getWidth();// 获得当前窗体的宽度
		int windowHeight = this.getHeight();// 获得当前窗体的高度
		this.setLocation((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2);

		this.setVisible(true);// 显示窗体

	}

	// 事件监听器
	private void registerListener() {

		// fd_save = new FileDialog(this,"保存文件",FileDialog.SAVE);
		FileDialog fd_open = new FileDialog(this, "打开文件", FileDialog.LOAD);
		Charset charset = Charset.forName(System.getProperty("file.encoding"));
		// encoder = charset.newEncoder();
		// decoder = charset.newDecoder();

		item_new.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newFile();

			}
		});
		item_open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = e.getActionCommand();

				fd_open.setVisible(true);
				String d = fd_open.getDirectory();
				String f = fd_open.getFile();
				if ((d != null) && (f != null)) {
					String destfile = d + f;
					if (destfile.equals(filename)) {
						return;
					} else {
						closeFile();
						filename = destfile;
						loadFile();
					}
				}
			}
		});
		item_save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (filename.equals("E:\\无标题.txt")) {
					saveasFile();
				} else {
					saveFile();

				}
			}
		});
		item_saveas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// fd_save.setVisible(true);
				saveasFile();
			}
		});
		// 退出
		item_exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exit();

			}
		});
		item_paste.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tv_area.paste();

			}
		});
		item_shear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tv_area.cut();

			}
		});
		item_copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tv_area.copy();

			}
		});
		item_del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tv_area.replaceSelection(null);// 删除
			}
		});
		item_selectall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tv_area.selectAll();
			}
		});
		autoswrap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (autoswrap.isSelected()) {
					tv_area.setLineWrap(true);
				} else {
					tv_area.setLineWrap(false);
				}
			}
		});
		item_date.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Date now = new Date(); // 获取当前时间
				SimpleDateFormat timenow = new SimpleDateFormat("yyyy年  MM月  dd日 \n");// 格式化当前日期时间 还要换行啊
				String date = timenow.format(now.getTime());
				String before;
				before = tv_area.getText();

				tv_area.setText(date + "\n" + before);

			}
		});
		item_about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutDialog(MyFrame.this, "关于-记事本", true);

			}
		});

	}

	// 新建的功能
	private void newFile() {
		if (!(tv_area.getText().equals(""))) {
			int res = JOptionPane.showConfirmDialog(null, "是否保存？", "提示信息", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION) {

				saveasFile();

			} else {
				tv_area.setText("");
			} // 清空记事本中的内容

		}
		filename = "E:\\无标题.txt";
		try (RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setTitle("记事本- " + filename);

	}

	public void closeFile() {

		filename = "";
		this.setTitle("记事本");

	}

	public boolean delFile(String strPath) {
		boolean filebool = false;
		File file = new File(strPath);
		if (file.exists() && file.isDirectory()) {
			if (file.listFiles().length == 0) {
				file.delete();
			} else {
				File[] ff = file.listFiles();
				for (int i = 0; i < ff.length; i++) {
					if (ff[i].isDirectory()) {
						delFile(strPath);
					}
					ff[i].delete();
				}
			}
		}
		filebool = true;
		return filebool;
	}

	public void saveFile() {

		delFile(filename);
		if (filename != null) {
			try {

				BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
				String str = tv_area.getText();
				bw.write(str);
				bw.newLine();
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.setTitle("记事本- " + filename);
	}

	public void saveasFile() {

		FileDialog fd_save = new FileDialog(this, "另存为", FileDialog.SAVE);
		fd_save.setVisible(true);
		String filename = fd_save.getDirectory() + fd_save.getFile();
		delFile(filename);
		if (filename != null) {
			try {

				BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
				String str = tv_area.getText();
				bw.write(str);
				bw.newLine();
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.setTitle("记事本- " + filename);
	}

	public void loadFile() {

		try {
			FileReader fr = new FileReader(filename); // 字符流 fileinputstream 字节流 （黑人问号）
			BufferedReader br = new BufferedReader(fr); // 处理流 （缓冲一下）
			String content;
			tv_area.setText("");
			while ((content = br.readLine()) != null) {

				tv_area.append(content + "\n");

			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println("找不到文件！原因如下：" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setTitle("记事本- " + filename);
	}

	private void exit() {
		newFile();
		System.exit(0);
	}

}

class AboutDialog extends JDialog {
	private JLabel desc = null;
	private JPanel panel = null;
	private JButton btn = null;

	public AboutDialog(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		init();
		registerListener();
	}

	private void init() {
		desc = new JLabel();
		desc.setText("<html>这是一个自定义记事本</html>");
		desc.setHorizontalAlignment(JLabel.CENTER);
		panel = new JPanel();
		btn = new JButton("ok");
		panel.add(btn);
		this.add(desc);
		this.add(panel, BorderLayout.SOUTH);
		this.setSize(300, 200);

	}

	private void registerListener() {
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				AboutDialog.this.dispose();// 退出对话框
			}
		});
		// 设置窗口居中显示
		// 定义一个工具包
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;// 获得屏幕的宽度
		int screenHeight = screenSize.height;// 获得屏幕的高度
		int dialogWidth = this.getWidth();// 获得当前窗体的宽度
		int dialogHeight = this.getHeight();// 获得当前窗体的高度
		this.setLocation((screenWidth - dialogWidth) / 2, (screenHeight - dialogHeight) / 2);

		this.setVisible(true);
	}
}
