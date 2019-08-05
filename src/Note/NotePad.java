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
		new MyFrame("���±�");
	}

}

class MyFrame extends JFrame {
	private JTextArea tv_area = null;
	private String filename = "";
	private JMenuBar mb = null;// �˵���
	private JMenu m_file = null;// �ļ��˵�
	private JMenu m_help = null;// �����˵�
	private JMenu m_edit = null;// �༭�˵�
	private JMenu m_format = null;// ��ʽ�˵�
	private JMenuItem item_new = null;// �½�
	private JMenuItem item_open = null;// ��
	private JMenuItem item_exit = null;// �˳�
	private JMenuItem item_save = null;// ����
	private JMenuItem item_saveas = null;// ���Ϊ
	private JMenuItem item_copy = null;// ����
	private JMenuItem item_paste = null;// ճ��
	private JMenuItem item_shear = null;// ����
	private JMenuItem item_del = null;// ɾ��
	private JMenuItem item_selectall = null;// ȫѡ
	private JMenuItem item_date = null;// ����
	private JCheckBoxMenuItem autoswrap = null;
	private JMenuItem item_about = null;// ����

	private JScrollPane s_pane = null;// �������������

	public MyFrame(String title) {
		super(title);
		init();// ��ʼ������
		registerListener();
	}

	// ��ʼ������
	private void init() {
		tv_area = new JTextArea();
		s_pane = new JScrollPane(tv_area);
		s_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Container cpane = this.getContentPane();
		cpane.add(s_pane, BorderLayout.CENTER);
		this.setSize(600, 400);// ���ô���Ĵ�С
		// ��ʼ���˵�
		mb = new JMenuBar();

		m_file = new JMenu("�ļ�");
		item_new = new JMenuItem("�½�");
		item_open = new JMenuItem("��");
		item_save = new JMenuItem("����");
		item_saveas = new JMenuItem("���Ϊ");
		item_exit = new JMenuItem("�˳�");
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

		m_help = new JMenu("����");
		item_about = new JMenuItem("����");
		m_help.add(item_about);

		m_edit = new JMenu("�༭");
		item_copy = new JMenuItem("����");
		item_paste = new JMenuItem("ճ��");
		item_shear = new JMenuItem("����");
		item_del = new JMenuItem("ɾ��");
		item_selectall = new JMenuItem("ȫѡ");
		item_date = new JMenuItem("����");
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

		m_format = new JMenu("��ʽ");
		autoswrap = new JCheckBoxMenuItem("�Զ�����", false);
		m_format.add(autoswrap);

		mb.add(m_file);
		mb.add(m_edit);
		mb.add(m_format);
		mb.add(m_help);
		// �Ѳ˵�����ӵ�������
		this.setJMenuBar(mb);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �رմ��ڵ�ʱ���˳�Ӧ�ó���
		// ���ô��ھ�����ʾ
		// ����һ�����߰�
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;// �����Ļ�Ŀ��
		int screenHeight = screenSize.height;// �����Ļ�ĸ߶�
		int windowWidth = this.getWidth();// ��õ�ǰ����Ŀ��
		int windowHeight = this.getHeight();// ��õ�ǰ����ĸ߶�
		this.setLocation((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2);

		this.setVisible(true);// ��ʾ����

	}

	// �¼�������
	private void registerListener() {

		// fd_save = new FileDialog(this,"�����ļ�",FileDialog.SAVE);
		FileDialog fd_open = new FileDialog(this, "���ļ�", FileDialog.LOAD);
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
				if (filename.equals("E:\\�ޱ���.txt")) {
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
		// �˳�
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
				tv_area.replaceSelection(null);// ɾ��
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
				Date now = new Date(); // ��ȡ��ǰʱ��
				SimpleDateFormat timenow = new SimpleDateFormat("yyyy��  MM��  dd�� \n");// ��ʽ����ǰ����ʱ�� ��Ҫ���а�
				String date = timenow.format(now.getTime());
				String before;
				before = tv_area.getText();

				tv_area.setText(date + "\n" + before);

			}
		});
		item_about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutDialog(MyFrame.this, "����-���±�", true);

			}
		});

	}

	// �½��Ĺ���
	private void newFile() {
		if (!(tv_area.getText().equals(""))) {
			int res = JOptionPane.showConfirmDialog(null, "�Ƿ񱣴棿", "��ʾ��Ϣ", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION) {

				saveasFile();

			} else {
				tv_area.setText("");
			} // ��ռ��±��е�����

		}
		filename = "E:\\�ޱ���.txt";
		try (RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setTitle("���±�- " + filename);

	}

	public void closeFile() {

		filename = "";
		this.setTitle("���±�");

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
		this.setTitle("���±�- " + filename);
	}

	public void saveasFile() {

		FileDialog fd_save = new FileDialog(this, "���Ϊ", FileDialog.SAVE);
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
		this.setTitle("���±�- " + filename);
	}

	public void loadFile() {

		try {
			FileReader fr = new FileReader(filename); // �ַ��� fileinputstream �ֽ��� �������ʺţ�
			BufferedReader br = new BufferedReader(fr); // ������ ������һ�£�
			String content;
			tv_area.setText("");
			while ((content = br.readLine()) != null) {

				tv_area.append(content + "\n");

			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println("�Ҳ����ļ���ԭ�����£�" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setTitle("���±�- " + filename);
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
		desc.setText("<html>����һ���Զ�����±�</html>");
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

				AboutDialog.this.dispose();// �˳��Ի���
			}
		});
		// ���ô��ھ�����ʾ
		// ����һ�����߰�
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;// �����Ļ�Ŀ��
		int screenHeight = screenSize.height;// �����Ļ�ĸ߶�
		int dialogWidth = this.getWidth();// ��õ�ǰ����Ŀ��
		int dialogHeight = this.getHeight();// ��õ�ǰ����ĸ߶�
		this.setLocation((screenWidth - dialogWidth) / 2, (screenHeight - dialogHeight) / 2);

		this.setVisible(true);
	}
}
