 
//�ζǴ�÷��ȣ + ���ʽ� 
class WinNums extends MyLottoNums {
	public static final int BONUS = 6;

	public WinNums() {
		setNums(autoSelect(7));

	}

	public WinNums(Integer... nums) {
		super(nums);

	}

	public Integer getBonusNum() {
		return getNums()[BONUS];
	}

	public Integer[] getNums() {
		return super.getNums();
	}

}