import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {

	private int limit; // ������ ����

	public JTextFieldLimit(int limit) // ������ : ������ ���̸� ���ڷ� ����

	{

		super();

		this.limit = limit;

	}

	// �ؽ�Ʈ �ʵ带 ä��� �޽�� : �������̵�

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;

		if (getLength() + str.length() <= limit)
			super.insertString(offset, str, attr);
	}
}