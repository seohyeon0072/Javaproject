import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {

	private int limit; // 제한할 길이

	public JTextFieldLimit(int limit) // 생성자 : 제한할 길이를 인자로 받음

	{

		super();

		this.limit = limit;

	}

	// 텍스트 필드를 채우는 메써드 : 오버라이드

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;

		if (getLength() + str.length() <= limit)
			super.insertString(offset, str, attr);
	}
}