package jp.sourceforge.andjong.mahjong;

import static jp.sourceforge.andjong.mahjong.Hai.ID_CHUN;
import static jp.sourceforge.andjong.mahjong.Hai.ID_HAKU;
import static jp.sourceforge.andjong.mahjong.Hai.ID_HATSU;
import static jp.sourceforge.andjong.mahjong.Hai.ID_NAN;
import static jp.sourceforge.andjong.mahjong.Hai.ID_PE;
import static jp.sourceforge.andjong.mahjong.Hai.ID_PIN_1;
import static jp.sourceforge.andjong.mahjong.Hai.ID_PIN_9;
import static jp.sourceforge.andjong.mahjong.Hai.ID_SHA;
import static jp.sourceforge.andjong.mahjong.Hai.ID_SOU_1;
import static jp.sourceforge.andjong.mahjong.Hai.ID_SOU_9;
import static jp.sourceforge.andjong.mahjong.Hai.ID_TON;
import static jp.sourceforge.andjong.mahjong.Hai.ID_WAN_1;
import static jp.sourceforge.andjong.mahjong.Hai.ID_WAN_9;

/**
 * �J�E���g�t�H�[�}�b�g���Ǘ�����N���X�ł��B
 *
 * @author Yuji Urushibara
 *
 */
public class CountFormat {
	/**
	 * �J�E���g���Ǘ�����N���X�ł��B
	 *
	 * @author Yuji Urushibara
	 *
	 */
	public static class Count {
		/** NK */
		public int m_noKind = 0;

		/** �� */
		public int m_num = 0;

		/**
		 * �J�E���g������������B
		 */
		public void initialize() {
			m_noKind = 0;
			m_num = 0;
		}
	}

	/**
	 * �オ��̑g�ݍ��킹�̃N���X�ł��B
	 *
	 * @author Yuji Urushibara
	 *
	 */
	public static class Combi {
		/** ����NK */
		public int m_atamaNoKind = 0;

		/** ���q��NK�̔z�� */
		public int[] m_shunNoKinds = new int[4];
		/** ���q��NK�̔z��̗L���Ȍ� */
		public int m_shunNum = 0;

		/** ���q��NK�̔z�� */
		public int[] m_kouNoKinds = new int[4];
		/** ���q��NK�̔z��̗L���Ȍ� */
		public int m_kouNum = 0;

		/**
		 * Combi���R�s�[����B
		 *
		 * @param a_dest
		 *            �R�s�[���Combi
		 * @param a_src
		 *            �R�s�[����Combi
		 */
		public static void copy(Combi a_dest, Combi a_src) {
			a_dest.m_atamaNoKind = a_src.m_atamaNoKind;

			a_dest.m_shunNum = a_src.m_shunNum;
			for (int i = 0; i < a_dest.m_shunNum; i++) {
				a_dest.m_shunNoKinds[i] = a_src.m_shunNoKinds[i];
			}

			a_dest.m_kouNum = a_src.m_kouNum;
			for (int i = 0; i < a_dest.m_kouNum; i++) {
				a_dest.m_kouNoKinds[i] = a_src.m_kouNoKinds[i];
			}
		}
	}

	/**
	 * ?オ???g???????z??????????N???X????B
	 *
	 * @author Yuji Urushibara
	 *
	 */
	private static class CombiManage {
		/** ?オ???g???????z?????l */
		public final static int COMBI_MAX = 10;

		/** ?オ???g???????z?? */
		public Combi[] m_combis = new Combi[COMBI_MAX];
		/** ?オ???g???????z???L?????? */
		public int m_combiNum = 0;

		/** ?J?E???g??z???c????? */
		public int m_remain = 0;

		/** ????? */
		public Combi m_work = new Combi();

		{
			for (int i = 0; i < m_combis.length; i++) {
				m_combis[i] = new Combi();
			}
		}

		/**
		 * ????????????????B
		 *
		 * @param a_remain
		 *            ?J?E???g??z???c?????
		 */
		public void initialize(int a_remain) {
			m_combiNum = 0;
			this.m_remain = a_remain;
			m_work.m_atamaNoKind = 0;
			m_work.m_shunNum = 0;
			m_work.m_kouNum = 0;
		}

		/**
		 * ?オ???g?????????????B
		 */
		public void add() {
			Combi.copy(m_combis[m_combiNum++], m_work);
		}
	}

	/** ?J?E???g????l */
	public static final int COUNT_MAX = 14 + 2;

	/** ?J?E???g??z?? */
	public Count[] m_counts;
	/** ?J?E???g??z???L?????? */
	public int m_countNum;

	/** ?オ???g???????z?????? */
	private CombiManage m_combiManage = new CombiManage();

	{
		m_counts = new Count[COUNT_MAX];
		for (int i = 0; i < COUNT_MAX; i++) {
			m_counts[i] = new Count();
		}
	}

	/**
	 * ?J?E???g??z??????????v???擾????B
	 *
	 * @return ?J?E???g??z??????????v
	 */
	private int getTotalCountLength() {
		int totalCountLength = 0;

		for (int i = 0; i < m_countNum; i++) {
			totalCountLength += m_counts[i].m_num;
		}

		return totalCountLength;
	}

	/**
	 * ?J?E???g?t?H?[?}?b?g???肷??B
	 *
	 * @param a_tehai
	 *            ??v
	 * @param a_addHai
	 *            ???????v
	 */
	public void setCountFormat(Tehai a_tehai, Hai a_addHai) {
		for (int i = 0; i < m_counts.length; i++) {
			m_counts[i].initialize();
		}
		m_countNum = 0;

		int addHaiNoKind = 0;
		boolean set = true;
		if (a_addHai != null) {
			addHaiNoKind = a_addHai.getNoKind();
			set = false;
		}

		int jyunTehaiNoKind;
		int jyunTehaiLength = a_tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength;) {
			jyunTehaiNoKind = (a_tehai.getJyunTehai())[i].getNoKind();

			if (!set && (jyunTehaiNoKind > addHaiNoKind)) {
				set = true;
				m_counts[m_countNum].m_noKind = addHaiNoKind;
				m_counts[m_countNum].m_num = 1;
				m_countNum++;
				continue;
			}

			m_counts[m_countNum].m_noKind = jyunTehaiNoKind;
			m_counts[m_countNum].m_num = 1;

			if (!set && (jyunTehaiNoKind == addHaiNoKind)) {
				set = true;
				m_counts[m_countNum].m_num++;
			}

			while (++i < jyunTehaiLength) {
				if (jyunTehaiNoKind == (a_tehai.getJyunTehai())[i].getNoKind()) {
					m_counts[m_countNum].m_num++;
				} else {
					break;
				}
			}

			m_countNum++;
		}

		if (!set) {
			m_counts[m_countNum].m_noKind = addHaiNoKind;
			m_counts[m_countNum].m_num = 1;
			m_countNum++;
		}

		for (int i = 0; i < m_countNum; i++) {
			if (m_counts[i].m_num > 4) {
				// 5???????v??????????B
				m_counts[i].m_num--;
			}
		}
	}

	/**
	 * ?オ???g???????z????擾????B
	 *
	 * @param a_combis
	 *            ?オ???g???????z??
	 * @return
	 */
	public int getCombis(Combi[] a_combis) {
//	public int getCombis(Combi[] a_combis) {
		m_combiManage.initialize(getTotalCountLength());
		searchCombi(0);
		//a_combis = m_combiManage.m_combis;
		if (m_combiManage.m_combiNum == 0) {
			m_chiitoitsu = checkChiitoitsu();
			if (m_chiitoitsu) {
				m_combiManage.m_combiNum = 1;
			} else {
				m_kokushi = checkKokushi();
				if (m_kokushi) {
					m_combiManage.m_combiNum = 1;
				}
			}
		}
		return m_combiManage.m_combiNum;
	}

	public Combi[] getCombis() {
		return m_combiManage.m_combis;
	}

	public int getCombiNum() {
		return m_combiManage.m_combiNum;
	}

	private boolean m_chiitoitsu;
	public boolean isChiitoitsu() {
		return m_chiitoitsu;
	}

	private boolean checkChiitoitsu() {
		int count = 0;
		for (int i = 0; i < m_countNum; i++) {
			if (m_counts[i].m_num == 2) {
				count++;
			} else {
				return false;
			}
		}

		if (count == 7) {
			return true;
		}
		return false;
	}

	private boolean m_kokushi;
	public boolean isKokushi() {
		return m_kokushi;
	}

	private boolean checkKokushi() {
		//?v??????邽???z?? (0??n??g?p??????j
		int checkId[] = {ID_WAN_1,ID_WAN_9,ID_PIN_1,ID_PIN_9,ID_SOU_1,ID_SOU_9,
								ID_TON,ID_NAN,ID_SHA,ID_PE,ID_HAKU,ID_HATSU,ID_CHUN};
		int countHai[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};

		//??v??ID??????????
		for(int i = 0 ; i < m_countNum ; i++){
			for(int j = 0 ; j < checkId.length ; j++){
				if(Hai.noKindToId(m_counts[i].m_noKind) == checkId[j]){
					countHai[j] = m_counts[i].m_num;
				}
			}
		}

		boolean atama = false;
		//???m???o????????????邩?????(??v???????1.9???v ??????P,?X???v???????????j
		for(int i = 0 ; i < countHai.length ; i++){
			//0????v??????Εs????
			if(countHai[i] == 0){
				return false;
			}
			if(countHai[i] == 2){
				atama = true;
			}
		}
		//??????????????????
		if (atama) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ?オ???g?????????A?I??T???B
	 *
	 * @param a_iSearch
	 *            ??????u
	 */
	private void searchCombi(int a_iSearch) {
		// ??????u???X?V????B
		for (; a_iSearch < m_countNum; a_iSearch++) {
			if (m_counts[a_iSearch].m_num > 0) {
				break;
			}
		}

		if (a_iSearch >= m_countNum) {
			return;
		}

		// ?????`?F?b?N????B
		if (m_combiManage.m_work.m_atamaNoKind == 0) {
			if (m_counts[a_iSearch].m_num >= 2) {
				// ?????m?肷??B
				m_counts[a_iSearch].m_num -= 2;
				m_combiManage.m_remain -= 2;
				m_combiManage.m_work.m_atamaNoKind = m_counts[a_iSearch].m_noKind;

				// ?オ???g??????????????????????B
				if (m_combiManage.m_remain <= 0) {
					m_combiManage.add();
				} else {
					searchCombi(a_iSearch);
				}

				// ?m?肵?????????B
				m_counts[a_iSearch].m_num += 2;
				m_combiManage.m_remain += 2;
				m_combiManage.m_work.m_atamaNoKind = 0;
			}
		}

		// ???q???`?F?b?N????B
		int left = a_iSearch;
		int center = a_iSearch + 1;
		int right = a_iSearch + 2;
		if (!Hai.isTsuu(m_counts[left].m_noKind)) {
			if ((m_counts[left].m_noKind + 1 == m_counts[center].m_noKind) && (m_counts[center].m_num > 0)) {
				if ((m_counts[left].m_noKind + 2 == m_counts[right].m_noKind) && (m_counts[right].m_num > 0)) {
					// ???q???m?肷??B
					m_counts[left].m_num--;
					m_counts[center].m_num--;
					m_counts[right].m_num--;
					m_combiManage.m_remain -= 3;
					m_combiManage.m_work.m_shunNoKinds[m_combiManage.m_work.m_shunNum] = m_counts[left].m_noKind;
					m_combiManage.m_work.m_shunNum++;

					// ?オ???g??????????????????????B
					if (m_combiManage.m_remain <= 0) {
						m_combiManage.add();
					} else {
						searchCombi(a_iSearch);
					}

					// ?m?肵?????q?????B
					m_counts[left].m_num++;
					m_counts[center].m_num++;
					m_counts[right].m_num++;
					m_combiManage.m_remain += 3;
					m_combiManage.m_work.m_shunNum--;
				}
			}
		}

		// ???q???`?F?b?N????B
		if (m_counts[a_iSearch].m_num >= 3) {
			// ???q???m?肷??B
			m_counts[a_iSearch].m_num -= 3;
			m_combiManage.m_remain -= 3;
			m_combiManage.m_work.m_kouNoKinds[m_combiManage.m_work.m_kouNum] = m_counts[a_iSearch].m_noKind;
			m_combiManage.m_work.m_kouNum++;

			// ?オ???g??????????????????????B
			if (m_combiManage.m_remain <= 0) {
				m_combiManage.add();
			} else {
				searchCombi(a_iSearch);
			}

			// ?m?肵?????q?????B/
			m_combiManage.m_remain += 3;
			m_counts[a_iSearch].m_num += 3;
			m_combiManage.m_work.m_kouNum--;
		}
	}
}
