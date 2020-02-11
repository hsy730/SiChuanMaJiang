package jp.sourceforge.andjong;

import jp.sourceforge.andjong.R;
import jp.sourceforge.andjong.DrawItem.PlayerInfo;
import static jp.sourceforge.andjong.DrawItem.*;
import jp.sourceforge.andjong.mahjong.EventIf;
import jp.sourceforge.andjong.mahjong.Fuuro;
import jp.sourceforge.andjong.mahjong.Hai;
import jp.sourceforge.andjong.mahjong.InfoUi;
import jp.sourceforge.andjong.mahjong.Hou;
import jp.sourceforge.andjong.mahjong.Mahjong;
import jp.sourceforge.andjong.mahjong.PlayerAction;
import jp.sourceforge.andjong.mahjong.SuteHai;
import jp.sourceforge.andjong.mahjong.Tehai;
import jp.sourceforge.andjong.mahjong.Yama;
import jp.sourceforge.andjong.mahjong.AgariScore.AgariInfo;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * 绘制麻将视图
 */
public class AndjongView extends View implements EventIf {
	private static final String TAG = "AndjongView";

	/** �A�N�e�B�r�e�B */
	private Game m_game;

	/** �v�̃C���[�W */
	private Bitmap[] m_haiImage;
	/** �v�̃C���[�W�̕� */
	private int m_haiImageWidth;
	/** �v�̃C���[�W�̍��� */
	private int m_haiImageHeight;

	/** 4个麻将底牌*/
	private static final int HAI_WIDTH = 50;
	/** �v�̍��� */
	private static final int HAI_HEIGHT = 23;

	private static final int TSUMO_OFFSET = 2;
	private static final int SELECT_OFFSET = 30;
//	private static final int SELECT_OFFSET = 16-4;// 选中待打出牌的偏移量

	private static final int FUURO_LEFT = 320 - 2;// 背面是红色的小麻将

	/** �v�̗��̃C���[�W */
	private Bitmap mHaiUraImage;

	/** ���ɂȂ����v�̃C���[�W */
	private Bitmap[] m_horizontalHaiImage;

	/** �B���Ă���v�̃C���[�W */
	private Bitmap m_hideHaiImage;

	private Bitmap[] m_largeHaiImage;
	private int m_largeHaiImageWidth;
//	private int m_largeHaiImageHeight;

	/** 1000�_�_�̃C���[�W */
	private Bitmap mTenbou1000Image;
	/** 100�_�_�̃C���[�W */
	private Bitmap mTenbou100Image;

	/** �N�ƃ}�[�N�̃C���[�W */
	private Bitmap mChiichaImage;

	/** �w�i�̃y�C���g */
	private Paint mBackgroundPaint;

	/*
	 * �ԃh��
	 */

	private Bitmap[] m_haiRedImage;
	private Bitmap[] m_horizontalHaiRedImage;
	private Bitmap[] m_largeHaiRedImage;

	/**
	 * 指的是弹出的方框消息，比如立直
	 */
	private static final int ROUND = 5;
	private static final int PRINT_AREA_WIDTH = 150 - 10;
	private static final int PRINT_AREA_HEIGHT = 134 - 10;
	private static final int PRINT_AREA_LEFT = 400 + 600 + 20;
	private static final int PRINT_AREA_TOP = 400 + 20;
	private static final int PRINT_AREA_RIGHT = PRINT_AREA_LEFT + PRINT_AREA_WIDTH;
	private static final int PRINT_AREA_BOTTOM = PRINT_AREA_TOP + PRINT_AREA_HEIGHT;
	private static final int PRINT_TEXT_SIZE = 30;
	private Paint m_printPaint;
	private RectF m_printRect;

	/**
	 * 提示消息框menu
	 */
//	private static final int MESSAGE_AREA_WIDTH = 88;
	private static final int MESSAGE_AREA_WIDTH = 146;
//	private static final int MESSAGE_AREA_HEIGHT = 40;
	private static final int MESSAGE_AREA_HEIGHT = 143;

	/** ���b�Z�[�W�G���A��Left */
	private static final int MESSAGE_AREA_LEFT = 392;
//	private static final int MESSAGE_AREA_LEFT = 87;
	/** ���b�Z�[�W�G���A��Top */
	private static final int MESSAGE_AREA_TOP = 280;
//	private static final int MESSAGE_AREA_MARGIN = 20;
//	private static final int MESSAGE_AREA_TOP = 176;
	private static final int MESSAGE_AREA_RIGHT = MESSAGE_AREA_LEFT + MESSAGE_AREA_WIDTH;
	private static final int MESSAGE_AREA_BOTTOM = MESSAGE_AREA_TOP + MESSAGE_AREA_HEIGHT;
	private static final int MESSAGE_TEXT_SIZE = 40;
	private static final int MESSAGE_ROUND = 20;
	private Paint mMessagePaint;
	private RectF mMessageRect;


	/**
	 * info area 指的是中间的展示消息区域，各自的剩余资金
	 * */
	private static final int INFO_AREA_WIDTH = 500;
	private static final int INFO_AREA_HEIGHT = 200;
	private static final int INFO_AREA_LEFT = 1000 - 250;
	private static final int INFO_AREA_TOP = 500 - 78;
	private static final int INFO_AREA_RIGHT = INFO_AREA_LEFT + INFO_AREA_WIDTH;
	private static final int INFO_AREA_BOTTOM = INFO_AREA_TOP + INFO_AREA_HEIGHT;

	/** ���G���A�̘g�̊ۂ� */
	private static final int INFO_ROUND = 10;

	/** ���G���A�̃y�C���g */
	private Paint m_infoPaint;

	/** ���G���A�̘g */
	private RectF m_infoRect;

	/**
	 * 提示框吃杠碰
	 */
	private static final int MENU_ITEM_MAX = 4;
	private static final int MENU_WIDTH = 94;
//	private static final int MENU_WIDTH = 78;
	private static final int MENU_HEIGHT = 66;
//	private static final int MENU_HEIGHT = 72;

	private static final int MENU_AREA_TOP = 250;
//	private static final int MENU_AREA_TOP = 406;
	private static final int MENU_AREA_LEFT = 0;
	private static final int MENU_AREA_TOP_MARGIN = 2;
//	private static final int MENU_AREA_TOP_MARGIN = 1;
	private static final int MENU_AREA_LEFT_MARGIN = 2;

	private RectF m_menuRect[];
	private static final int MENU_TEXT_SIZE = 24;

	/*
	 * ���ʉ��
	 */

	/** ���ʉ�ʂ̃h����top */
	private static final int RESULT_DORAS_TOP = 2;
	/** ���ʉ�ʂ̃h����left */
	private static final int RESULT_DORAS_LEFT = 2;

	/** �`��A�C�e�� */
	private DrawItem m_drawItem = new DrawItem();

	/** InfoUI */
	private InfoUi m_infoUi;

	/** UI�̖��O */
	private String mName;

	/** �v���C���[�A�N�V���� */
	private PlayerAction m_playerAction;
	// 设置中间一堆展示的位置
	private static final int LEFT_OFFSET = 1000;
	private static final int TOP_OFFSET = 100;

	/** 文字"东一局" */
	private static final int KYOKU_LEFT = 1000 + LEFT_OFFSET;// 东一局
	private static final int KYOKU_TOP = 400 + 44 + TOP_OFFSET;
	private static final int KYOKU_TEXT_SIZE = 50;

	/** 盖着的4张牌 */
	private static final int DORAS_LEFT = 1000 + LEFT_OFFSET;
	private static final int DORAS_TOP = 616 + TOP_OFFSET;

	/** 扁平红色1筒 */
	private static final int TENBOU_01000_MIN_IMAGE_LEFT = 800 + LEFT_OFFSET;
	private static final int TENBOU_01000_MIN_IMAGE_TOP = 450 + TOP_OFFSET;

	/** ���[�`�_�̐���Left */
	private static final int REACHBOU_LEFT = TENBOU_01000_MIN_IMAGE_LEFT + 43;
	/** ���[�`�_�̐�Top */
	private static final int REACHBOU_TOP = TENBOU_01000_MIN_IMAGE_TOP + 5;

	/** �������̃e�L�X�g�T�C�Y */
	private static final int MINI_TEXT_SIZE = 18;

	/** 东西南北 */
	private static final int[] TENBO_LEFT = { 1040 + LEFT_OFFSET, 1188 + LEFT_OFFSET, 1040 + LEFT_OFFSET, 892 + LEFT_OFFSET };
	private static final int[] TENBO_TOP = { 540 + TOP_OFFSET, 492 + TOP_OFFSET, 444 + TOP_OFFSET, 492 + TOP_OFFSET };

	/** �{��̃C���[�W��Left */
	private static final int TENBOU_00100_MIN_IMAGE_LEFT = 680 + LEFT_OFFSET;
	/** �{��̃C���[�W��Top */
	private static final int TENBOU_00100_MIN_IMAGE_TOP = TENBOU_01000_MIN_IMAGE_TOP;

	/** �{��̐���Left */
	private static final int HONBA_LEFT = TENBOU_00100_MIN_IMAGE_LEFT + 172;
	/** �{��̐�Top */
	private static final int HONBA_TOP = TENBOU_00100_MIN_IMAGE_TOP + 20;

	private String m_settingKyokusuu;

	public int getKyokusuu() {
		int kyokusuu = 3;
		if (m_settingKyokusuu.equals("hanchan")) {
			kyokusuu = 7;
		}
		Log.d(TAG, "kyokusuu = " + kyokusuu);
		return kyokusuu;
	}

	// �b��B
	public boolean isKuitan() {
		return Settings.isKuitan(this.m_game);
	}

	// �b��B
	public boolean isAkaDora() {
		return Settings.isAkaDora(this.m_game);
	}

	/**
	 * 麻将界面
	 *
	 * @param context
	 *            �A�N�e�B�r�e�B
	 */
	public AndjongView(Context context) {
		super(context);

		// �A�N�e�B�r�e�B��ۑ�����B
		this.m_game = (Game) context;

		// 加载麻将牌图片资源
		initImage(getResources(),context);

		// �y�C���g������������B
		initPaint(getResources());

		// UI�������������܂ő҂B
		m_drawItem.setState(STATE_INIT_WAIT);

		// �t�H�[�J�X��L���ɂ���B
		setFocusable(true);
		setFocusableInTouchMode(true);

		m_settingKyokusuu = Settings.getKyokusuu(context);
		Log.d(TAG, "m_settingKyokusuu = " + m_settingKyokusuu);
	}

	private Bitmap m_playerImage;
	private Bitmap m_kamichaImage;
	private Bitmap m_toimenImage;
	private Bitmap m_shimochaImage;

	/**
	 * �C���[�W������������B
	 *
	 * @param res ���\�[�X
	 */
	private void initImage(Resources res,Context context) {
		// 下方我方区域
		m_playerImage = Bitmap.createBitmap(1200, 500, Bitmap.Config.ARGB_4444);
		m_kamichaImage = Bitmap.createBitmap(KAWA_TEHAI_AREA_HEIGHT, KAWA_TEHAI_AREA_WIDTH, Bitmap.Config.ARGB_4444);
		m_toimenImage = Bitmap.createBitmap(KAWA_TEHAI_AREA_WIDTH, KAWA_TEHAI_AREA_HEIGHT, Bitmap.Config.ARGB_4444);
		m_shimochaImage = Bitmap.createBitmap(KAWA_TEHAI_AREA_HEIGHT, KAWA_TEHAI_AREA_WIDTH, Bitmap.Config.ARGB_4444);

		m_haiImage = new Bitmap[Hai.ID_MAX + 1];
		// 绘制万
		m_haiImage[0] = BitmapFactory.decodeResource(res, R.drawable.hai_00_wan_1);
		m_haiImage[1] = BitmapFactory.decodeResource(res, R.drawable.hai_01_wan_2);
		m_haiImage[2] = BitmapFactory.decodeResource(res, R.drawable.hai_02_wan_3);
		m_haiImage[3] = BitmapFactory.decodeResource(res, R.drawable.hai_03_wan_4);
		m_haiImage[4] = BitmapFactory.decodeResource(res, R.drawable.hai_04_wan_5);
		m_haiImage[5] = BitmapFactory.decodeResource(res, R.drawable.hai_05_wan_6);
		m_haiImage[6] = BitmapFactory.decodeResource(res, R.drawable.hai_06_wan_7);
		m_haiImage[7] = BitmapFactory.decodeResource(res, R.drawable.hai_07_wan_8);
		m_haiImage[8] = BitmapFactory.decodeResource(res, R.drawable.hai_08_wan_9);
		// 绘制饼
		m_haiImage[9] = BitmapFactory.decodeResource(res, R.drawable.hai_09_pin_1);
		m_haiImage[10] = BitmapFactory.decodeResource(res, R.drawable.hai_10_pin_2);
		m_haiImage[11] = BitmapFactory.decodeResource(res, R.drawable.hai_11_pin_3);
		m_haiImage[12] = BitmapFactory.decodeResource(res, R.drawable.hai_12_pin_4);
		m_haiImage[13] = BitmapFactory.decodeResource(res, R.drawable.hai_13_pin_5);
		m_haiImage[14] = BitmapFactory.decodeResource(res, R.drawable.hai_14_pin_6);
		m_haiImage[15] = BitmapFactory.decodeResource(res, R.drawable.hai_15_pin_7);
		m_haiImage[16] = BitmapFactory.decodeResource(res, R.drawable.hai_16_pin_8);
		m_haiImage[17] = BitmapFactory.decodeResource(res, R.drawable.hai_17_pin_9);
		// 绘制条
		m_haiImage[18] = BitmapFactory.decodeResource(res, R.drawable.hai_18_sou_1);
		m_haiImage[19] = BitmapFactory.decodeResource(res, R.drawable.hai_19_sou_2);
		m_haiImage[20] = BitmapFactory.decodeResource(res, R.drawable.hai_20_sou_3);
		m_haiImage[21] = BitmapFactory.decodeResource(res, R.drawable.hai_21_sou_4);
		m_haiImage[22] = BitmapFactory.decodeResource(res, R.drawable.hai_22_sou_5);
		m_haiImage[23] = BitmapFactory.decodeResource(res, R.drawable.hai_23_sou_6);
		m_haiImage[24] = BitmapFactory.decodeResource(res, R.drawable.hai_24_sou_7);
		m_haiImage[25] = BitmapFactory.decodeResource(res, R.drawable.hai_25_sou_8);
		m_haiImage[26] = BitmapFactory.decodeResource(res, R.drawable.hai_26_sou_9);

		m_haiImage[27] = BitmapFactory.decodeResource(res, R.drawable.hai_27_ton);
		m_haiImage[28] = BitmapFactory.decodeResource(res, R.drawable.hai_28_nan);
		m_haiImage[29] = BitmapFactory.decodeResource(res, R.drawable.hai_29_sha);
		m_haiImage[30] = BitmapFactory.decodeResource(res, R.drawable.hai_30_pei);

		m_haiImage[31] = BitmapFactory.decodeResource(res, R.drawable.hai_31_haku);
		m_haiImage[32] = BitmapFactory.decodeResource(res, R.drawable.hai_32_hatsu);
		m_haiImage[33] = BitmapFactory.decodeResource(res, R.drawable.hai_33_chun);

		m_haiImageWidth = m_haiImage[0].getWidth();
		m_haiImageHeight = m_haiImage[0].getHeight();

		mHaiUraImage = BitmapFactory.decodeResource(res, R.drawable.hai_ura);

		m_horizontalHaiImage = new Bitmap[Hai.ID_MAX + 1];

		for (int i = 0; i < m_horizontalHaiImage.length; i++) {
			m_horizontalHaiImage[i] = createHorizontalBitmap(m_haiImage[i]);
		}

		m_hideHaiImage = BitmapFactory.decodeResource(res, R.drawable.hai_hide);

		mTenbou1000Image = BitmapFactory.decodeResource(res, R.drawable.tenbou_1000);
		mTenbou100Image = BitmapFactory.decodeResource(res, R.drawable.tenbou_100);

		mChiichaImage = BitmapFactory.decodeResource(res, R.drawable.chiicha);

		/*
		 * �傫���v�̃C���[�W
		 */

		m_largeHaiImage = new Bitmap[Hai.ID_MAX + 1];

		m_largeHaiImage[0] = BitmapFactory.decodeResource(res, R.drawable.hai_00_wan_1_l);
		m_largeHaiImage[1] = BitmapFactory.decodeResource(res, R.drawable.hai_01_wan_2_l);
		m_largeHaiImage[2] = BitmapFactory.decodeResource(res, R.drawable.hai_02_wan_3_l);
		m_largeHaiImage[3] = BitmapFactory.decodeResource(res, R.drawable.hai_03_wan_4_l);
		m_largeHaiImage[4] = BitmapFactory.decodeResource(res, R.drawable.hai_04_wan_5_l);
		m_largeHaiImage[5] = BitmapFactory.decodeResource(res, R.drawable.hai_05_wan_6_l);
		m_largeHaiImage[6] = BitmapFactory.decodeResource(res, R.drawable.hai_06_wan_7_l);
		m_largeHaiImage[7] = BitmapFactory.decodeResource(res, R.drawable.hai_07_wan_8_l);
		m_largeHaiImage[8] = BitmapFactory.decodeResource(res, R.drawable.hai_08_wan_9_l);

		m_largeHaiImage[9] = BitmapFactory.decodeResource(res, R.drawable.hai_09_pin_1_l);
		m_largeHaiImage[10] = BitmapFactory.decodeResource(res, R.drawable.hai_10_pin_2_l);
		m_largeHaiImage[11] = BitmapFactory.decodeResource(res, R.drawable.hai_11_pin_3_l);
		m_largeHaiImage[12] = BitmapFactory.decodeResource(res, R.drawable.hai_12_pin_4_l);
		m_largeHaiImage[13] = BitmapFactory.decodeResource(res, R.drawable.hai_13_pin_5_l);
		m_largeHaiImage[14] = BitmapFactory.decodeResource(res, R.drawable.hai_14_pin_6_l);
		m_largeHaiImage[15] = BitmapFactory.decodeResource(res, R.drawable.hai_15_pin_7_l);
		m_largeHaiImage[16] = BitmapFactory.decodeResource(res, R.drawable.hai_16_pin_8_l);
		m_largeHaiImage[17] = BitmapFactory.decodeResource(res, R.drawable.hai_17_pin_9_l);

		m_largeHaiImage[18] = BitmapFactory.decodeResource(res, R.drawable.hai_18_sou_1_l);
		m_largeHaiImage[19] = BitmapFactory.decodeResource(res, R.drawable.hai_19_sou_2_l);
		m_largeHaiImage[20] = BitmapFactory.decodeResource(res, R.drawable.hai_20_sou_3_l);
		m_largeHaiImage[21] = BitmapFactory.decodeResource(res, R.drawable.hai_21_sou_4_l);
		m_largeHaiImage[22] = BitmapFactory.decodeResource(res, R.drawable.hai_22_sou_5_l);
		m_largeHaiImage[23] = BitmapFactory.decodeResource(res, R.drawable.hai_23_sou_6_l);
		m_largeHaiImage[24] = BitmapFactory.decodeResource(res, R.drawable.hai_24_sou_7_l);
		m_largeHaiImage[25] = BitmapFactory.decodeResource(res, R.drawable.hai_25_sou_8_l);
		m_largeHaiImage[26] = BitmapFactory.decodeResource(res, R.drawable.hai_26_sou_9_l);

		m_largeHaiImage[27] = BitmapFactory.decodeResource(res, R.drawable.hai_27_ton_l);
		m_largeHaiImage[28] = BitmapFactory.decodeResource(res, R.drawable.hai_28_nan_l);
		m_largeHaiImage[29] = BitmapFactory.decodeResource(res, R.drawable.hai_29_sha_l);
		m_largeHaiImage[30] = BitmapFactory.decodeResource(res, R.drawable.hai_30_pei_l);

		m_largeHaiImage[31] = BitmapFactory.decodeResource(res, R.drawable.hai_31_haku_l);
		m_largeHaiImage[32] = BitmapFactory.decodeResource(res, R.drawable.hai_32_hatsu_l);
		m_largeHaiImage[33] = BitmapFactory.decodeResource(res, R.drawable.hai_33_chun_l);

		m_largeHaiImageWidth = m_largeHaiImage[0].getWidth();
//		m_largeHaiImageHeight = m_largeHaiImage[0].getHeight();

		/*
		 * �ԃh���̃C���[�W
		 */

		m_haiRedImage = new Bitmap[Hai.ID_MAX + 1];
		m_haiRedImage[4] = BitmapFactory.decodeResource(res, R.drawable.hai_04_wan_5_red);
		m_haiRedImage[13] = BitmapFactory.decodeResource(res, R.drawable.hai_13_pin_5_red);
		m_haiRedImage[22] = BitmapFactory.decodeResource(res, R.drawable.hai_22_sou_5_red);

		m_horizontalHaiRedImage = new Bitmap[Hai.ID_MAX + 1];
		m_horizontalHaiRedImage[4] = createHorizontalBitmap(m_haiRedImage[4]);
		m_horizontalHaiRedImage[13] = createHorizontalBitmap(m_haiRedImage[13]);
		m_horizontalHaiRedImage[22] = createHorizontalBitmap(m_haiRedImage[22]);

		m_largeHaiRedImage = new Bitmap[Hai.ID_MAX + 1];
		m_largeHaiRedImage[4] = BitmapFactory.decodeResource(res, R.drawable.hai_04_wan_5_l_red);
		m_largeHaiRedImage[13] = BitmapFactory.decodeResource(res, R.drawable.hai_13_pin_5_l_red);
		m_largeHaiRedImage[22] = BitmapFactory.decodeResource(res, R.drawable.hai_22_sou_5_l_red);
	}

	private Bitmap getHaiImage(Hai a_hai) {
		if (a_hai.isRed()) {
			return m_haiRedImage[a_hai.getId()];
		} else {
			return m_haiImage[a_hai.getId()];
		}
	}

	private Bitmap getHorizontalHaiImage(Hai a_hai) {
		if (a_hai.isRed()) {
			return m_horizontalHaiRedImage[a_hai.getId()];
		} else {
			return m_horizontalHaiImage[a_hai.getId()];
		}
	}

	private Bitmap getLargeHaiImage(Hai a_hai) {
		if (a_hai.isRed()) {
			return m_largeHaiRedImage[a_hai.getId()];
		} else {
			return m_largeHaiImage[a_hai.getId()];
		}
	}

	/**
	 * �y�C���g������������B
	 *
	 * @param res
	 *            ���\�[�X
	 */
	private void initPaint(Resources res) {
		mBackgroundPaint = new Paint();
		// 桌面背景颜色
		mBackgroundPaint.setColor(res.getColor(R.color.andjong_background));

		mMessagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		//mMessagePaint.setColor(R.color.menu);
		mMessagePaint.setColor(res.getColor(R.color.menu));
		mMessageRect = new RectF(MESSAGE_AREA_LEFT, MESSAGE_AREA_TOP, MESSAGE_AREA_RIGHT + MESSAGE_ROUND, MESSAGE_AREA_BOTTOM + MESSAGE_ROUND);

		// ���j���[������������B
		m_menuRect = new RectF[MENU_ITEM_MAX];
		int left;
		int top = MENU_AREA_TOP + MENU_AREA_TOP_MARGIN;
		int right;
		int bottom = top + MENU_HEIGHT;
		for (int i = 0; i < m_menuRect.length; i++) {
			left = (MENU_AREA_LEFT + MENU_AREA_LEFT_MARGIN) + ((MENU_WIDTH + (MENU_AREA_LEFT_MARGIN * 2)) * i);
			right = left + MENU_WIDTH;
			m_menuRect[m_menuRect.length - 1 - i] = new RectF(left, top, right, bottom);
			//m_menuRect[i] = new RectF(left, top, right, bottom);
		}

		m_infoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		m_infoPaint.setColor(res.getColor(R.color.info));
		m_infoRect = new RectF(INFO_AREA_LEFT, INFO_AREA_TOP, INFO_AREA_RIGHT, INFO_AREA_BOTTOM);

		m_printPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		m_printPaint.setColor(Color.WHITE);
		m_printPaint.setAlpha(192);
		m_printRect = new RectF(PRINT_AREA_LEFT, PRINT_AREA_TOP, PRINT_AREA_RIGHT, PRINT_AREA_BOTTOM);
	}

	/**
	 * ���ɂȂ����C���[�W���쐬����B
	 *
	 * @param verticalImage
	 *            �c�̃C���[�W
	 * @return ���ɂȂ����C���[�W
	 */
	private Bitmap createHorizontalBitmap(Bitmap verticalImage) {
		int height = verticalImage.getWidth();
		int width = verticalImage.getHeight();
		Bitmap horizontalImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(horizontalImage);
		canvas.rotate(270, 0, 0);
		canvas.drawBitmap(verticalImage, -height, 0, null);
		return horizontalImage;
	}

	/**
	 * UI������������B
	 *
	 * @param infoUi
	 *            InfoUi
	 * @param name
	 *            UI�̖��O
	 */
	public void initUi(InfoUi infoUi, String name) {
		this.m_infoUi = infoUi;
		this.mName = name;

		// �v���C���[�A�N�V�������擾����B
		m_playerAction = m_infoUi.getPlayerAction();

		// ��ԂȂ��ɂ��Ă����B
		m_drawItem.setState(STATE_NONE);
	}

	boolean m_isValidChiiLeft;
	boolean m_isValidChiiCenter;
	boolean m_isValidChiiRight;

	@Override
	protected void onDraw(Canvas a_canvas) {
		Resources res = getResources();
		// �w�i��`�悷��B
		a_canvas.drawRect(0, 0, getWidth(), getHeight(), mBackgroundPaint);

		synchronized (m_drawItem) {
			if (m_drawItem.m_state == STATE_KYOKU_START) {
				// �ǂ�\������B
				drawString(240, 160, a_canvas, 30, Color.WHITE, m_drawItem.getKyokuString(), Align.CENTER);
				return;
			} else if (m_drawItem.m_state == STATE_RESULT) {
				// �h����\������B
				drawDoraHais(RESULT_DORAS_LEFT, RESULT_DORAS_TOP, a_canvas, m_infoUi.getDoraHais());

				SuteHai[] suteHais = m_drawItem.m_playerInfos[m_drawItem.m_kazeFrom].m_kawa.getSuteHais();
				int kawaLength = m_drawItem.m_playerInfos[m_drawItem.m_kazeFrom].m_kawa.getSuteHaisLength();
				for (int i = 0; i < kawaLength; i++) {
					if (suteHais[i].isReach()) {
						// ���h����\������B
						drawDoraHais(RESULT_DORAS_LEFT, RESULT_DORAS_TOP + HAI_HEIGHT, a_canvas, m_infoUi.getUraDoraHais());
						break;
					}
				}

				if (m_drawItem.m_eventId == EventId.TSUMO_AGARI) {
					drawPlayerTehai(a_canvas, m_drawItem.m_playerInfos[m_drawItem.m_kazeFrom].m_tehai, m_drawItem.m_playerInfos[m_drawItem.m_kazeFrom].m_tsumoHai, 15, 2, 50);
				} else {
					drawPlayerTehai(a_canvas, m_drawItem.m_playerInfos[m_drawItem.m_kazeFrom].m_tehai, m_drawItem.m_suteHai, 15, 2, 50);
				}

				AgariInfo agariInfo = m_infoUi.getAgariInfo();
				int left = 2;
				int top = 50 + 44 + 2 + 10;
				for (int i = 0; i < agariInfo.m_yakuNames.length; i++) {
					drawString(left, top, a_canvas, 18, Color.WHITE, agariInfo.m_yakuNames[i], Align.LEFT);
					top += 18;
				}
				String string = new String();
				String han = res.getString(R.string.han);
				String ten = res.getString(R.string.ten);
				String fu = res.getString(R.string.fu);
				String sp = res.getString(R.string.space);
				if (agariInfo.m_score.m_oyaRon >= 48000) {
					string += res.getString(R.string.yakuman) + sp + agariInfo.m_agariScore + ten;
				} else if (agariInfo.m_score.m_oyaRon >= 36000) {
					string += agariInfo.m_han + han + sp + res.getString(R.string.sanbaiman) + sp + agariInfo.m_agariScore + ten;
				} else if (agariInfo.m_score.m_oyaRon >= 24000) {
					string += agariInfo.m_han + han + sp + res.getString(R.string.baiman) + sp + agariInfo.m_agariScore + ten;
				} else if (agariInfo.m_score.m_oyaRon >= 18000) {
					string += agariInfo.m_han + han + sp + res.getString(R.string.haneman) + sp + agariInfo.m_agariScore + ten;
				} else if (agariInfo.m_score.m_oyaRon >= 12000) {
					string += agariInfo.m_han + han + sp + agariInfo.m_fu + fu + sp + res.getString(R.string.mangan) + sp + agariInfo.m_agariScore + ten;
				} else {
					string += agariInfo.m_han + han + sp + agariInfo.m_fu + fu + sp + agariInfo.m_agariScore + ten;
				}
				drawString(left, top + 20, a_canvas, 20, Color.WHITE, string, Align.LEFT);
				return;
			}

			a_canvas.drawRoundRect(m_infoRect, INFO_ROUND, INFO_ROUND, m_infoPaint);

			switch (m_drawItem.m_state) {
			case STATE_INIT_WAIT:
			case STATE_NONE:
				// �����`�悵�Ȃ��B
				return;
			case STATE_REACH:
				// ���[�`�̃��b�Z�[�W��\������B
				drawPrint(a_canvas, res.getString(R.string.info_reach));
				break;
			case STATE_RON:
				// �����̃��b�Z�[�W��\������B
				drawPrint(a_canvas, res.getString(R.string.info_ron));
				break;
			case STATE_TSUMO:
				// �c���̃��b�Z�[�W��\������B
				drawPrint(a_canvas, res.getString(R.string.info_tsumo));
				break;
			case STATE_RYUUKYOKU:
				// ���ǂ̃��b�Z�[�W��\������B
				drawPrint(a_canvas, res.getString(R.string.info_ryuukyoku));
				break;
			case STATE_END:
				drawPrint(a_canvas, res.getString(R.string.info_end));
				break;
			}

			// �ǂ�\������B
			drawString(KYOKU_LEFT - 30, KYOKU_TOP, a_canvas, KYOKU_TEXT_SIZE, Color.WHITE, m_drawItem.getKyokuString(), Align.CENTER);

			// �c��v��\������B
			drawString(KYOKU_LEFT + 40, KYOKU_TOP, a_canvas, KYOKU_TEXT_SIZE, Color.WHITE, new Integer(m_drawItem.m_tsumoRemain).toString(), Align.CENTER);

			// ���[�`�_�̐���\������B
			drawReachbou(a_canvas, m_drawItem.getReachbou());

			// �{���\������B
			drawHonba(a_canvas, m_drawItem.getHonba());

			// �h����\������B
			drawDoraHais(DORAS_LEFT, DORAS_TOP, a_canvas, m_infoUi.getDoraHais());

			int manKaze = m_infoUi.getManKaze();
			int dispKaze[] = { 0, 1, 2, 3 };
			for (int i = 0; i < 4; i++) {
				dispKaze[i] = manKaze;
				manKaze++;
				manKaze %= 4;
			}

			String[] kazeStrings = res.getStringArray(R.array.kaze);
			String sp = res.getString(R.string.space);

			// �_�_��\������B
			for (int i = 0; i < EventIf.KAZE_KIND_NUM; i++) {
				drawString(TENBO_LEFT[i], TENBO_TOP[i], a_canvas, MINI_TEXT_SIZE, Color.WHITE, kazeStrings[dispKaze[i]] + sp + new Integer(m_drawItem.m_playerInfos[dispKaze[i]].m_tenbo).toString(), Align.CENTER);
			}

			// �N�ƃ}�[�N��\������B
			drawChiicha(a_canvas, m_drawItem.getChiicha());

			Bitmap test3 = getKawaTehaiAreaImage(m_drawItem.m_playerInfos[dispKaze[1]].m_tehai, m_drawItem.m_playerInfos[dispKaze[1]].m_kawa, PLACE_KAMICHA, dispKaze[1], m_drawItem.m_playerInfos[dispKaze[1]].m_tenpai, m_drawItem.m_playerInfos[dispKaze[1]].m_tsumoHai);
			a_canvas.drawBitmap(test3, KAWA_TEHAI_AREA_KAMICHA_LEFT, KAWA_TEHAI_AREA_KAMICHA_TOP, null);

			Bitmap test = getKawaTehaiAreaImage(m_drawItem.m_playerInfos[dispKaze[2]].m_tehai, m_drawItem.m_playerInfos[dispKaze[2]].m_kawa, PLACE_TOIMEN, dispKaze[2], m_drawItem.m_playerInfos[dispKaze[2]].m_tenpai, m_drawItem.m_playerInfos[dispKaze[2]].m_tsumoHai);
			a_canvas.drawBitmap(test, KAWA_TEHAI_AREA_TOIMEN_LEFT, KAWA_TEHAI_AREA_TOIMEN_TOP, null);

			Bitmap test4 = getKawaTehaiAreaImage(m_drawItem.m_playerInfos[dispKaze[3]].m_tehai, m_drawItem.m_playerInfos[dispKaze[3]].m_kawa, PLACE_SHIMOCHA, dispKaze[3], m_drawItem.m_playerInfos[dispKaze[3]].m_tenpai, m_drawItem.m_playerInfos[dispKaze[3]].m_tsumoHai);
			a_canvas.drawBitmap(test4, KAWA_TEHAI_AREA_SHIMOCHA_LEFT, KAWA_TEHAI_AREA_SHIMOCHA_TOP, null);

			Bitmap test2 = getKawaTehaiAreaImage(m_drawItem.m_playerInfos[dispKaze[0]].m_tehai, m_drawItem.m_playerInfos[dispKaze[0]].m_kawa, PLACE_PLAYER, dispKaze[0], true, m_drawItem.m_playerInfos[dispKaze[0]].m_tsumoHai);
			a_canvas.drawBitmap(test2, KAWA_TEHAI_AREA_PLAYER_LEFT, KAWA_TEHAI_AREA_PLAYER_TOP, null);

			// �A�N�V�����{�^����\������B
			boolean actionRequest = m_playerAction.isActionRequest();
			if (actionRequest) {
				if (!m_playerAction.isDispMenu()) {
					drawMessage(a_canvas, res.getString(R.string.button_menu));
				} else {
					int iMenu = 0;

					if (m_playerAction.isValidReach()) {
						drawMenuMessage(a_canvas, res.getString(R.string.button_reach), iMenu);
						iMenu++;
					}

					if (m_playerAction.isValidRon()) {
						drawMenuMessage(a_canvas, res.getString(R.string.button_ron), iMenu);
						iMenu++;
					}

					if (m_playerAction.isValidTsumo()) {
						drawMenuMessage(a_canvas, res.getString(R.string.button_tsumo), iMenu);
						iMenu++;
					}

					if (m_playerAction.isValidPon()) {
						drawMenuMessage(a_canvas, res.getString(R.string.button_pon), iMenu);
						iMenu++;
					}

					m_isValidChiiLeft = m_playerAction.isValidChiiLeft();
					m_isValidChiiCenter = m_playerAction.isValidChiiCenter();
					m_isValidChiiRight = m_playerAction.isValidChiiRight();

					if (m_isValidChiiLeft || m_isValidChiiCenter || m_isValidChiiRight) {
						drawMenuMessage(a_canvas, res.getString(R.string.button_chii), iMenu);
						iMenu++;
					}

					if (m_playerAction.isValidKan() || m_playerAction.isValidDaiMinKan()) {
						drawMenuMessage(a_canvas, res.getString(R.string.button_kan), iMenu);
						iMenu++;
					}
				}
			}
		}
	}

	/**
	 * ���b�Z�[�W��\������B
	 *
	 * @param canvas
	 *            �L�����o�X
	 * @param string
	 *            ������
	 */
	private void drawMessage(Canvas canvas, String string) {
		canvas.drawRoundRect(mMessageRect, MESSAGE_ROUND, MESSAGE_ROUND, mMessagePaint);
		drawString((MESSAGE_AREA_LEFT + MESSAGE_AREA_RIGHT) / 2, (MESSAGE_AREA_TOP + MESSAGE_AREA_BOTTOM) / 2, canvas, MESSAGE_TEXT_SIZE, Color.WHITE, string, Align.CENTER);
	}


	private void drawMenuMessage(Canvas canvas, String string, int no) {
		canvas.drawRoundRect(m_menuRect[no], ROUND, ROUND, mMessagePaint);
		drawString((int) (m_menuRect[no].left + (MENU_WIDTH / 2)), (int) (m_menuRect[no].top + (MENU_HEIGHT / 4)), canvas, MENU_TEXT_SIZE, Color.WHITE, string, Align.CENTER);
		//drawString((int) (m_menuRect[no].left + (MENU_WIDTH / 2)), (int) (m_menuRect[no].top + (MENU_HEIGHT / 2)), canvas, MENU_TEXT_SIZE, Color.WHITE, string, Align.CENTER);
	}

	private void drawPrint(Canvas a_canvas, String a_string) {
		a_canvas.drawRoundRect(m_printRect, ROUND, ROUND, m_printPaint);
		drawString((PRINT_AREA_LEFT + PRINT_AREA_RIGHT) / 2, (PRINT_AREA_TOP + PRINT_AREA_BOTTOM) / 2, a_canvas, PRINT_TEXT_SIZE, Color.BLACK, a_string, Align.CENTER);
	}

	/**
	 * �������\������B
	 *
	 * @param left
	 *            Left
	 * @param top
	 *            Top
	 * @param canvas
	 *            �L�����o�X
	 * @param textSize
	 *            �e�L�X�g�T�C�Y
	 * @param color
	 *            �F
	 * @param string
	 *            ������
	 */
	private void drawString(int left, int top, Canvas canvas, int textSize,
			int color, String string, Align a_align) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setTextSize(textSize);
		paint.setTextAlign(a_align);

		canvas.drawText(string, left, top - ((paint.ascent() + paint.descent()) / 2), paint);
	}

	/**
	 * �h����\������B
	 *
	 * @param a_left
	 *            left
	 * @param a_top
	 *            top
	 * @param a_canvas
	 *            �L�����o�X
	 * @param a_hais
	 *            �v�̔z��
	 */
	private void drawDoraHais(int a_left, int a_top, Canvas a_canvas, Hai a_hais[]) {
		int i = 0;
		for (; i < a_hais.length; i++) {
			a_canvas.drawBitmap(getHaiImage(a_hais[i]), a_left + (i * HAI_WIDTH), a_top, null);
		}

		for (; i < Yama.DORA_HAIS_MAX; i++) {
			a_canvas.drawBitmap(mHaiUraImage, a_left + (i * HAI_WIDTH), a_top, null);
		}
	}

	/**
	 * ���[�`�_�̐���\������B
	 *
	 * @param canvas
	 *            �L�����o�X
	 * @param reachbou
	 *            ���[�`�_�̐�
	 */
	private void drawReachbou(Canvas canvas, int reachbou) {
		canvas.drawBitmap(mTenbou1000Image, TENBOU_01000_MIN_IMAGE_LEFT, TENBOU_01000_MIN_IMAGE_TOP, null);

		Resources res = getResources();
		String string = res.getString(R.string.multi) + res.getString(R.string.space) + new Integer(reachbou).toString();
		drawString(REACHBOU_LEFT, REACHBOU_TOP, canvas, MINI_TEXT_SIZE, Color.WHITE, string, Align.CENTER);
	}

	/**
	 * �{���\������B
	 *
	 * @param canvas
	 *            �L�����o�X
	 * @param honba
	 *            �{��
	 */
	private void drawHonba(Canvas canvas, int honba) {
		canvas.drawBitmap(mTenbou100Image, TENBOU_00100_MIN_IMAGE_LEFT, TENBOU_00100_MIN_IMAGE_TOP, null);

		Resources res = getResources();
		String string = res.getString(R.string.multi) + res.getString(R.string.space) + new Integer(honba).toString();
		drawString(HONBA_LEFT, HONBA_TOP, canvas, MINI_TEXT_SIZE, Color.WHITE, string, Align.CENTER);
	}

	/**
	 * �N�ƃ}�[�N��\������B
	 *
	 * @param canvas
	 *            �L�����o�X
	 * @param chiicha
	 *            �N��
	 */
	private void drawChiicha(Canvas canvas, int chiicha) {
		canvas.drawBitmap(mChiichaImage, TENBO_LEFT[chiicha] + 29, TENBO_TOP[chiicha] - 5, null);
		//canvas.drawBitmap(mChiichaImage, TENBO_LEFT[chiicha] - 26, TENBO_TOP[chiicha] - 5, null);
	}

	private static final int PLACE_PLAYER = 0;
	private static final int PLACE_KAMICHA = 1;
	private static final int PLACE_TOIMEN = 2;
	private static final int PLACE_SHIMOCHA = 3;

	private static final int KAWA_TEHAI_AREA_WIDTH = 900;
	private static final int KAWA_TEHAI_AREA_HEIGHT = 400;
//	private static final int KAWA_TEHAI_AREA_HEIGHT = 85;

	private static final int TEHAI_LEFT = 100;// 四个方向未打出麻将牌向右的偏移量
//	private static final int TEHAI_LEFT = 2;
//	private static final int TEHAI_TOP = 47;
	private static final int TEHAI_TOP = 100;//打出的历史牌与未打出牌的间隔

	private static final int KAWA_LEFT = 42;// 四个方向已经打出的历史麻将牌向右的偏移量
//	private static final int KAWA_LEFT = 49;
	private static final int KAWA_TOP = 0;
	/**
	 * 下方我方麻将位置
	 */
	private static final int KAWA_TEHAI_AREA_PLAYER_LEFT = 600;
	private static final int KAWA_TEHAI_AREA_PLAYER_TOP = 904;
//	private static final int KAWA_TEHAI_AREA_PLAYER_LEFT = 0;
//	private static final int KAWA_TEHAI_AREA_PLAYER_TOP = 321;
	/**
	 * 上方麻将位置
	 */
	private static final int KAWA_TEHAI_AREA_TOIMEN_LEFT = 500;
//	private static final int KAWA_TEHAI_AREA_TOIMEN_LEFT = 0;
	private static final int KAWA_TEHAI_AREA_TOIMEN_TOP = 0;

	/**
	 * 右侧的麻将位置
	 */
	private static final int KAWA_TEHAI_AREA_KAMICHA_LEFT = 2000;
	private static final int KAWA_TEHAI_AREA_KAMICHA_TOP = 0;
//	private static final int KAWA_TEHAI_AREA_KAMICHA_LEFT = 235;
//	private static final int KAWA_TEHAI_AREA_KAMICHA_TOP = 47;
	/**
	 * 左侧麻将位置
	 */
	private static final int KAWA_TEHAI_AREA_SHIMOCHA_LEFT = 0;
	private static final int KAWA_TEHAI_AREA_SHIMOCHA_TOP = 100;
//	private static final int KAWA_TEHAI_AREA_SHIMOCHA_LEFT = 0;
//	private static final int KAWA_TEHAI_AREA_SHIMOCHA_TOP = 38;

	private Bitmap getKawaTehaiAreaImage(Tehai tehai, Hou kawa, int place, int kaze, boolean isPlayer, Hai tsumoHai) {
		int width;
		int height;
		int kawaLeft = KAWA_LEFT;
		int kawaTop = KAWA_TOP;
		Bitmap image;
		Canvas canvas;

		switch (place) {
		case PLACE_PLAYER:
			width = 408;
			height = 94;
			if ((m_playerImage == null) || m_playerImage.isRecycled()) {
				m_playerImage = Bitmap.createBitmap(1632, 376, Bitmap.Config.ARGB_4444);
			}
			image = m_playerImage;
			image.eraseColor(Color.TRANSPARENT);
			canvas = new Canvas(image);

			kawaLeft += 40;
			break;
		case PLACE_KAMICHA:
			width = KAWA_TEHAI_AREA_HEIGHT;
			height = KAWA_TEHAI_AREA_WIDTH;
			//m_kamichaImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			if ((m_kamichaImage == null) || m_kamichaImage.isRecycled()) {
				m_kamichaImage = Bitmap.createBitmap(KAWA_TEHAI_AREA_HEIGHT, KAWA_TEHAI_AREA_WIDTH, Bitmap.Config.ARGB_4444);
			}
			image = m_kamichaImage;
			image.eraseColor(Color.TRANSPARENT);
			canvas = new Canvas(image);
			canvas.rotate(270, 0, 0);
			canvas.translate(-height, 0);

			//kawaLeft += 6;
			break;
		case PLACE_TOIMEN:
			width = KAWA_TEHAI_AREA_WIDTH;
			height = KAWA_TEHAI_AREA_HEIGHT;
			//m_toimenImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			if ((m_toimenImage == null) || m_toimenImage.isRecycled()) {
				m_toimenImage = Bitmap.createBitmap(KAWA_TEHAI_AREA_WIDTH, KAWA_TEHAI_AREA_HEIGHT, Bitmap.Config.ARGB_4444);
			}
			image = m_toimenImage;
			image.eraseColor(Color.TRANSPARENT);
			canvas = new Canvas(image);
			canvas.rotate(180, 0, 0);
			canvas.translate(-width, -height);
			break;
		case PLACE_SHIMOCHA:
			width = KAWA_TEHAI_AREA_HEIGHT;
			height = KAWA_TEHAI_AREA_WIDTH;
			//m_shimochaImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			if ((m_shimochaImage == null) || m_shimochaImage.isRecycled()) {
				m_shimochaImage = Bitmap.createBitmap(KAWA_TEHAI_AREA_HEIGHT, KAWA_TEHAI_AREA_WIDTH, Bitmap.Config.ARGB_4444);
			}
			image = m_shimochaImage;
			image.eraseColor(Color.TRANSPARENT);
			canvas = new Canvas(image);
			canvas.rotate(90, 0, 0);
			canvas.translate(0, -width);
			break;
		default:
			return null;
		}

		drawHou(canvas, kawa, kawaLeft, kawaTop, null);

		if ((m_infoUi.getManKaze() == kaze)) {
	//	if ((mInfoUi.getManKaze() == kaze) && (drawItem.tsumoKaze == kaze)) {
			drawPlayerTehai(canvas, tehai, tsumoHai, m_iSelectSutehai, TEHAI_LEFT, 50 - 4);
		} else {
			//m_drawItem.m_isDebug = true;
			drawTehai(canvas, tehai, tsumoHai, isPlayer | m_drawItem.m_isDebug, TEHAI_LEFT/*26*/, TEHAI_TOP);
		}

		return image;
	}

	private void drawHou(Canvas a_canvas, Hou a_hou, int a_left, int a_top, Paint a_paint) {
		SuteHai[] suteHais = a_hou.getSuteHais();
		int suteHaisLength = a_hou.getSuteHaisLength();

		int left = a_left;
		int top;
		boolean reachFlag = false;
		int count = 0;
		int kiriCount = 12;

		for (int i = 0; i < suteHaisLength; i++) {
			if (count == kiriCount) {
				left = a_left;
				a_top += m_haiImageHeight;
				kiriCount += 12;
			}

			if (suteHais[i].isReach() || reachFlag) {
				if (!suteHais[i].isNaki()) {
					top = a_top + ((m_haiImageHeight - m_haiImageWidth) / 2);
					a_canvas.drawBitmap(getHorizontalHaiImage(suteHais[i]), left, top, a_paint);
					left += m_haiImageHeight;
					reachFlag = false;
					count++;
				} else {
					reachFlag = true;
				}
			} else {
				if (!suteHais[i].isNaki()) {
					a_canvas.drawBitmap(getHaiImage(suteHais[i]), left, a_top, a_paint);
					left += m_haiImageWidth;
					count++;
				}
			}
		}
	}

	private void drawTehai(Canvas a_canvas, Tehai a_tehai, Hai a_addHai, boolean a_disp, int a_left, int a_top) {
		Hai[] jyunTehai = a_tehai.getJyunTehai();
		int jyunTehaiLength = a_tehai.getJyunTehaiLength();

		int iSkip = m_drawItem.getISkip();

		int haiImageWidth = this.m_haiImageWidth;

		int left = a_left;
		int top = a_top + ((haiImageWidth * 2) - m_haiImageHeight);

		for (int i = 0; i < jyunTehaiLength; i++, left += haiImageWidth) {
			if ((a_addHai != null) && (m_drawItem.m_state == STATE_RIHAI_WAIT) && (i == iSkip)) {
				continue;
			}

			a_canvas.drawBitmap(a_disp ? getHaiImage(jyunTehai[i]) : m_hideHaiImage, left, top, null);
		}

		if (a_addHai != null) {
			left += TSUMO_OFFSET;
			a_canvas.drawBitmap(a_disp ? getHaiImage(a_addHai) : m_hideHaiImage, left, top, null);
		}

		drawFuuros(a_canvas, a_tehai, FUURO_LEFT, top);
	}

	private void drawFuuros(Canvas a_canvas, Tehai a_tehai, int a_left, int a_top) {
		int fuuroNums = a_tehai.getFuuroNum();
		if (fuuroNums > 0) {
			Fuuro[] fuuros = a_tehai.getFuuros();
			for (int i = 0; i < fuuroNums; i++) {
				Hai hais[] = fuuros[i].getHais();
				int type = fuuros[i].getType();

				if (type == Fuuro.TYPE_ANKAN) {
					a_left -= m_haiImageWidth;
					a_canvas.drawBitmap(mHaiUraImage, a_left, a_top, null);
					a_left -= m_haiImageWidth;
					a_canvas.drawBitmap(getHaiImage(hais[2]), a_left, a_top, null);
					a_left -= m_haiImageWidth;
					a_canvas.drawBitmap(getHaiImage(hais[1]), a_left, a_top, null);
					a_left -= m_haiImageWidth;
					a_canvas.drawBitmap(mHaiUraImage, a_left, a_top, null);
				} else {
					int relation = fuuros[i].getRelation();

					if (relation == Mahjong.RELATION_SHIMOCHA) {
						a_left -= m_haiImageHeight;
						a_canvas.drawBitmap(getHorizontalHaiImage(hais[2]), a_left, a_top + 4, null);
						if (type == Fuuro.TYPE_KAKAN) {
							a_canvas.drawBitmap(getHorizontalHaiImage(hais[3]), a_left, a_top - 15, null);
						} else if (type == Fuuro.TYPE_DAIMINKAN) {
							a_left -= m_haiImageWidth;
							a_canvas.drawBitmap(getHaiImage(hais[3]), a_left, a_top, null);
						}
						a_left -= m_haiImageWidth;
						a_canvas.drawBitmap(getHaiImage(hais[1]), a_left, a_top, null);
						a_left -= m_haiImageWidth;
						a_canvas.drawBitmap(getHaiImage(hais[0]), a_left, a_top, null);
					} else if (relation == Mahjong.RELATION_TOIMEN) {
						a_left -= m_haiImageWidth;
						a_canvas.drawBitmap(getHaiImage(hais[2]), a_left, a_top, null);
						if (type == Fuuro.TYPE_DAIMINKAN) {
							a_left -= m_haiImageWidth;
							a_canvas.drawBitmap(getHaiImage(hais[3]), a_left, a_top, null);
						}
						a_left -= m_haiImageHeight;
						a_canvas.drawBitmap(getHorizontalHaiImage(hais[1]), a_left, a_top + 4, null);
						if (type == Fuuro.TYPE_KAKAN) {
							a_canvas.drawBitmap(getHorizontalHaiImage(hais[3]), a_left, a_top - 15, null);
						}
						a_left -= m_haiImageWidth;
						a_canvas.drawBitmap(getHaiImage(hais[0]), a_left, a_top, null);
					} else {
						a_left -= m_haiImageWidth;
						a_canvas.drawBitmap(getHaiImage(hais[2]), a_left, a_top, null);
						a_left -= m_haiImageWidth;
						a_canvas.drawBitmap(getHaiImage(hais[1]), a_left, a_top, null);
						if (type == Fuuro.TYPE_DAIMINKAN) {
							a_left -= m_haiImageWidth;
							a_canvas.drawBitmap(getHaiImage(hais[3]), a_left, a_top, null);
						}
						a_left -= m_haiImageHeight;
						a_canvas.drawBitmap(getHorizontalHaiImage(hais[0]), a_left, a_top + 4, null);
						if (type == Fuuro.TYPE_KAKAN) {
							a_canvas.drawBitmap(getHorizontalHaiImage(hais[3]), a_left, a_top - 15, null);
						}
					}
				}
			}
		}
	}

	private void drawPlayerTehai(Canvas a_canvas, Tehai a_tehai, Hai a_addHai, int a_iSelect, int a_left, int a_top) {
		Hai[] jyunTehai = a_tehai.getJyunTehai();
		int jyunTehaiLength = a_tehai.getJyunTehaiLength();

		int iSkip = m_drawItem.getISkip();

		//Bitmap haiImage[] = this.m_largeHaiImage;
		int haiImageWidth = this.m_largeHaiImageWidth;

		int left = a_left;
		int topSelect = a_top - SELECT_OFFSET;
		int top = a_top;
		//int top = topSelect + SELECT_OFFSET;

		if (m_playerAction.getState() == PlayerAction.STATE_CHII_SELECT) {
			EventId chiiEventId = m_playerAction.getChiiEventId();
			Hai iSelects[];
			switch (chiiEventId) {
			case CHII_LEFT:
				iSelects = m_playerAction.getSarachiHaiLeft();
				break;
			case CHII_CENTER:
				iSelects = m_playerAction.getSarachiHaiCenter();
				break;
			default:
				iSelects = m_playerAction.getSarachiHaiRight();
				break;
			}
			int iSelect = 0;

			for (int i = 0; i < jyunTehaiLength; i++, left += haiImageWidth) {
				if ((a_addHai != null) && (m_drawItem.m_state == STATE_RIHAI_WAIT) && (i == iSkip)) {
					continue;
				}

				if ((iSelect < 2) &&(jyunTehai[i].getId() == iSelects[iSelect].getId())) {
				//if ((jyunTehai[i].getId() == iSelects[0].getId()) || (jyunTehai[i].getId() == iSelects[1].getId())) {
					iSelect++;
					a_canvas.drawBitmap(getLargeHaiImage(jyunTehai[i]), left, topSelect, null);
				} else {
					a_canvas.drawBitmap(getLargeHaiImage(jyunTehai[i]), left, top, null);
				}
			}
		} else if (m_playerAction.getState() == PlayerAction.STATE_KAN_SELECT) {
			Hai[] kanHais = m_playerAction.getKanHais();
			//int kanNum = m_playerAction.getKanNum();
			int kanSelect = m_playerAction.getKanSelect();

			for (int i = 0; i < jyunTehaiLength; i++, left += haiImageWidth) {
				if ((a_addHai != null) && (m_drawItem.m_state == STATE_RIHAI_WAIT) && (i == iSkip)) {
					continue;
				}

				if (jyunTehai[i].getId() == kanHais[kanSelect].getId()) {
					a_canvas.drawBitmap(getLargeHaiImage(jyunTehai[i]), left, topSelect, null);
				} else {
					a_canvas.drawBitmap(getLargeHaiImage(jyunTehai[i]), left, top, null);
				}
			}

			if (a_addHai != null) {
				left += TSUMO_OFFSET;
				if ((a_addHai.getId() == kanHais[kanSelect].getId()) && (m_drawItem.m_state != STATE_RIHAI_WAIT) && (m_drawItem.m_state != STATE_RESULT)) {
					a_canvas.drawBitmap(getLargeHaiImage(a_addHai), left, topSelect, null);
				} else {
					a_canvas.drawBitmap(getLargeHaiImage(a_addHai), left, top, null);
				}
			}
		} else if (m_playerAction.getState() == PlayerAction.STATE_REACH_SELECT){
			int[] indexs = m_playerAction.m_indexs;
			a_iSelect = indexs[m_playerAction.getReachSelect()];
			for (int i = 0; i < jyunTehaiLength; i++, left += haiImageWidth) {
				if ((a_addHai != null) && (m_drawItem.m_state == STATE_RIHAI_WAIT) && (i == iSkip)) {
					continue;
				}

				if (i == a_iSelect) {
					a_canvas.drawBitmap(getLargeHaiImage(jyunTehai[i]), left, topSelect, null);
				} else {
					a_canvas.drawBitmap(getLargeHaiImage(jyunTehai[i]), left, top, null);
				}
			}

			if (a_addHai != null) {
				left += TSUMO_OFFSET;
				if (a_iSelect >= jyunTehaiLength) {
					a_canvas.drawBitmap(getLargeHaiImage(a_addHai), left, topSelect, null);
				} else {
					a_canvas.drawBitmap(getLargeHaiImage(a_addHai), left, top, null);
				}
			}
		} else {
			for (int i = 0; i < jyunTehaiLength; i++, left += haiImageWidth) {
				if ((a_addHai != null) && (m_drawItem.m_state == STATE_RIHAI_WAIT) && (i == iSkip)) {
					continue;
				}

				if ((i == a_iSelect) && (m_playerAction.getState() == PlayerAction.STATE_SUTEHAI_SELECT)) {
					a_canvas.drawBitmap(getLargeHaiImage(jyunTehai[i]), left, topSelect, null);
				} else {
					a_canvas.drawBitmap(getLargeHaiImage(jyunTehai[i]), left, top, null);
				}
			}

			if (a_addHai != null) {
				left += TSUMO_OFFSET;
				if ((a_iSelect >= jyunTehaiLength) && (m_drawItem.m_state != STATE_RIHAI_WAIT) && (m_drawItem.m_state != STATE_RESULT)) {
					a_canvas.drawBitmap(getLargeHaiImage(a_addHai), left, topSelect, null);
				} else {
					a_canvas.drawBitmap(getLargeHaiImage(a_addHai), left, top, null);
				}
			}
		}

		drawFuuros(a_canvas, a_tehai, FUURO_LEFT + 32, top + 11);
	}
	// 表示麻将触摸感应的的位置
	private static final int TOUCH_TOP = 1000;
	private static final int TOUCH_BOTTOM = 1100;
//	private static final int TOUCH_TOP = 480 - 97;
//	private static final int TOUCH_BOTTOM = 480;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (m_drawItem.m_state) {
		case STATE_END:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				m_game.setResult(Activity.RESULT_OK);
				m_game.finish();
				return true;
			}
			break;
		}

		if (m_drawItem.m_kazeFrom >= 4) {
			m_playerAction.actionNotifyAll();
			return true;
		}

		int x = (int) event.getX();
		int y = (int) event.getY();
		int action = event.getAction();

		Log.d(TAG, "onTouchEvent: x = " + x + ", y = " + y);

		if (action == MotionEvent.ACTION_UP) {
			//return true;
		}

		boolean actionRequest = m_playerAction.isActionRequest();
		if (actionRequest) {
			if (action == MotionEvent.ACTION_DOWN) {
				if (!m_playerAction.isDispMenu()) {
					if ((160 <= y) && (y <= MESSAGE_AREA_BOTTOM)) {
					//if ((MESSAGE_AREA_TOP - MESSAGE_AREA_MARGIN <= y) && (y <= MESSAGE_AREA_BOTTOM)) {
						if ((MESSAGE_AREA_LEFT <= x) && (x <= MESSAGE_AREA_RIGHT)) {
							m_playerAction.setDispMenu(true);
							invalidate();
							return true;
						}
					}
					if (m_playerAction.getState() != PlayerAction.STATE_SUTEHAI_SELECT) {
						m_playerAction.actionNotifyAll();
						return true;
					}
				} else {
					int iMenu = 5;
					for (int i = 0; i < m_menuRect.length; i++) {
						if (x >= m_menuRect[i].left && x <= m_menuRect[i].right) {
							if (y >= 160 && y <= m_menuRect[i].bottom) {
							//if (y >= m_menuRect[i].top && y <= m_menuRect[i].bottom) {
								iMenu = i;
								break;
							}
						}
					}
					if (iMenu >= m_playerAction.getMenuNum() && (m_playerAction.getState() != PlayerAction.STATE_RON_SELECT)) {
						m_playerAction.setDispMenu(false);
						invalidate();
						return true;
					}
					m_playerAction.setMenuSelect(iMenu);
					m_playerAction.actionNotifyAll();

					return true;
				}
			}
		}

		if (m_playerAction.getState() == PlayerAction.STATE_REACH_SELECT) {
			int indexNum = m_playerAction.m_indexNum;
			int reachSelect = m_playerAction.getReachSelect();
			//int[] indexs = m_playerAction.m_indexs;
			if (action == MotionEvent.ACTION_DOWN) {
				int ty = (int) event.getY();
				if ((TOUCH_TOP <= ty) && (ty <= TOUCH_BOTTOM)) {
					reachSelect++;
					if (reachSelect >= indexNum) {
						reachSelect = 0;
					}
					m_playerAction.setReachSelect(reachSelect);
					invalidate();
				} else {
					m_playerAction.actionNotifyAll();
				}
			}

			return true;
		}

		if (m_playerAction.getState() == PlayerAction.STATE_KAN_SELECT) {
			int kanNum = m_playerAction.getKanNum();
			int kanSelect = m_playerAction.getKanSelect();
			if (action == MotionEvent.ACTION_DOWN) {
				int ty = (int) event.getY();
				if ((TOUCH_TOP <= ty) && (ty <= TOUCH_BOTTOM)) {
					kanSelect++;
					if (kanSelect >= kanNum) {
						kanSelect = 0;
					}
					m_playerAction.setKanSelect(kanSelect);
					invalidate();
				} else {
					m_playerAction.actionNotifyAll();
				}
			}

			return true;
		}

		if (m_playerAction.getState() == PlayerAction.STATE_CHII_SELECT) {
			boolean isValidChiiLeft = m_isValidChiiLeft;
			boolean isValidChiiCenter = m_isValidChiiCenter;
			boolean isValidChiiRight = m_isValidChiiRight;
			EventId chiiEventId = m_playerAction.getChiiEventId();
			if (action == MotionEvent.ACTION_DOWN) {
				//int tx = (int) event.getX();
				int ty = (int) event.getY();
				if ((TOUCH_TOP <= ty) && (ty <= TOUCH_BOTTOM)) {
					switch (chiiEventId) {
					case CHII_LEFT:
						if (isValidChiiRight) {
							m_playerAction.setChiiEventId(EventId.CHII_RIGHT);
						} else {
							m_playerAction.setChiiEventId(EventId.CHII_CENTER);
						}
						break;
					case CHII_RIGHT:
						if (isValidChiiCenter) {
							m_playerAction.setChiiEventId(EventId.CHII_CENTER);
						} else {
							m_playerAction.setChiiEventId(EventId.CHII_LEFT);
						}
						break;
					default:
						if (isValidChiiLeft) {
							m_playerAction.setChiiEventId(EventId.CHII_LEFT);
						} else {
							m_playerAction.setChiiEventId(EventId.CHII_RIGHT);
						}
						break;
					}
					invalidate();
				} else {
					m_isValidChiiLeft = m_isValidChiiCenter = m_isValidChiiRight = false;
					m_playerAction.actionNotifyAll();
				}
			}
			return true;
		}

		if (action == MotionEvent.ACTION_DOWN) {
			synchronized (m_drawItem) {
				switch (m_drawItem.m_state) {
				case STATE_PLAY:
					break;
				default:
					m_playerAction.actionNotifyAll();
					return true;
				}
			}
		}

		PlayerInfo playerInfo;
		try {
			synchronized (m_drawItem) {
				playerInfo = m_drawItem.m_playerInfos[m_drawItem.m_kazeFrom];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
			return true;
		}
		int jyunTehaiLength = playerInfo.m_tehai.getJyunTehaiLength();
		if (playerInfo.m_tsumoHai != null) {
			jyunTehaiLength++;
		}
		jyunTehaiLength--;

		/* X,Y���W�̎擾 */
		int tx = (int) event.getX();
		int ty = (int) event.getY();
		int act_evt = event.getAction();

		/* Y���W�̔���(�v�̍����̊�) */
		if ((TOUCH_TOP <= ty) && (ty <= TOUCH_BOTTOM)) {
			if (m_drawItem.m_isManReach) {
				m_iSelectSutehai = jyunTehaiLength;
			} else {
				int iSelect = (tx - 46) / m_largeHaiImageWidth;
				if (iSelect > jyunTehaiLength) {
					iSelect = jyunTehaiLength;
				} else if (iSelect < 0) {
					iSelect = 0;
				}
				if (m_iSelectSutehai == iSelect) {
					return true;
				}
				m_iSelectSutehai = iSelect;
				invalidate(0, 240, 480, 320);
				return true;
			}
		} else {
			if (act_evt == MotionEvent.ACTION_DOWN) {
				synchronized (m_drawItem) {
					switch (m_drawItem.m_state) {
					case STATE_PLAY:
						m_playerAction.setSutehaiIdx(m_iSelectSutehai);
						m_playerAction.actionNotifyAll();
						break;
					default:
						m_playerAction.actionNotifyAll();
						break;
					}
				}
			}
		}
		/* �ĕ`��̎w�� */
		invalidate();
		return true;
	}


	private int m_iSelectSutehai = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
		if (m_drawItem.m_state == STATE_END) {
			return super.onKeyDown(keyCode, event);
		}

		if (m_drawItem.m_kazeFrom >= 4) {
			m_playerAction.actionNotifyAll();
			return true;
		}

		boolean actionRequest = m_playerAction.isActionRequest();
		int menuSelect = m_playerAction.getMenuSelect();
		if (actionRequest) {
			if (!m_playerAction.isDispMenu()) {
				//m_playerAction.setDispMenu(true);
			} else {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_LEFT:
					menuSelect--;
					menuSelect %= 2;
					m_playerAction.setMenuSelect(menuSelect);
					break;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					menuSelect++;
					menuSelect %= 2;
					m_playerAction.setMenuSelect(menuSelect);
					break;
				case KeyEvent.KEYCODE_ENTER:
				case KeyEvent.KEYCODE_DPAD_CENTER:
					m_playerAction.actionNotifyAll();
					break;
				default:
					return super.onKeyDown(keyCode, event);
				}

				invalidate();
				return true;
			}
		}

		int state = m_playerAction.getState();
		if (state == PlayerAction.STATE_REACH_SELECT) {
			int indexNum = m_playerAction.m_indexNum;
			int reachSelect = m_playerAction.getReachSelect();
			//int[] indexs = m_playerAction.m_indexs;
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				reachSelect--;
				if (reachSelect < 0) {
					reachSelect = indexNum - 1;
				}
				m_playerAction.setReachSelect(reachSelect);
				invalidate();
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				reachSelect++;
				if (reachSelect >= indexNum) {
					reachSelect = 0;
				}
				m_playerAction.setReachSelect(reachSelect);
				invalidate();
				break;
			default:
				//return super.onKeyDown(keyCode, event);
			}
		}
		if (state == PlayerAction.STATE_KAN_SELECT) {
			int kanNum = m_playerAction.getKanNum();
			int kanSelect = m_playerAction.getKanSelect();
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				kanSelect--;
				if (kanSelect < 0) {
					kanSelect = kanNum - 1;
				}
				m_playerAction.setKanSelect(kanSelect);
				invalidate();
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				kanSelect++;
				if (kanSelect >= kanNum) {
					kanSelect = 0;
				}
				m_playerAction.setKanSelect(kanSelect);
				invalidate();
				break;
			default:
				//return super.onKeyDown(keyCode, event);
			}
		}
		if (state == PlayerAction.STATE_CHII_SELECT) {
			boolean isValidChiiLeft = m_isValidChiiLeft;
			boolean isValidChiiCenter = m_isValidChiiCenter;
			boolean isValidChiiRight = m_isValidChiiRight;
			EventId chiiEventId = m_playerAction.getChiiEventId();
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				switch (chiiEventId) {
				case CHII_RIGHT:
					if (isValidChiiLeft) {
						m_playerAction.setChiiEventId(EventId.CHII_LEFT);
					} else {
						m_playerAction.setChiiEventId(EventId.CHII_CENTER);
					}
					break;
				case CHII_CENTER:
					if (isValidChiiRight) {
						m_playerAction.setChiiEventId(EventId.CHII_RIGHT);
					} else {
						m_playerAction.setChiiEventId(EventId.CHII_LEFT);
					}
					break;
				default:
					if (isValidChiiCenter) {
						m_playerAction.setChiiEventId(EventId.CHII_CENTER);
					} else {
						m_playerAction.setChiiEventId(EventId.CHII_RIGHT);
					}
					break;
				}
				invalidate();
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				switch (chiiEventId) {
				case CHII_LEFT:
					if (isValidChiiRight) {
						m_playerAction.setChiiEventId(EventId.CHII_RIGHT);
					} else {
						m_playerAction.setChiiEventId(EventId.CHII_CENTER);
					}
					break;
				case CHII_RIGHT:
					if (isValidChiiCenter) {
						m_playerAction.setChiiEventId(EventId.CHII_CENTER);
					} else {
						m_playerAction.setChiiEventId(EventId.CHII_LEFT);
					}
					break;
				default:
					if (isValidChiiLeft) {
						m_playerAction.setChiiEventId(EventId.CHII_LEFT);
					} else {
						m_playerAction.setChiiEventId(EventId.CHII_RIGHT);
					}
					break;
				}
				invalidate();
				break;
			default:
				//return super.onKeyDown(keyCode, event);
			}
			//invalidate();
			//return true;
		}

		PlayerInfo playerInfo = m_drawItem.m_playerInfos[m_drawItem.m_kazeFrom];
		int jyunTehaiLength = playerInfo.m_tehai.getJyunTehaiLength();
		if (playerInfo.m_tsumoHai != null) {
			jyunTehaiLength++;
		}
		jyunTehaiLength--;

		//int state = m_playerAction.getState();
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			if (state != PlayerAction.STATE_SUTEHAI_SELECT) {
				return true;
			}
			//mSelectSutehaiIdx = 0;
			if(m_drawItem.m_isDebug){
				//m_drawItem.m_isDebug = false;
			}else{
				//m_drawItem.m_isDebug = true;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			if (state != PlayerAction.STATE_SUTEHAI_SELECT) {
				return true;
			}
			//mSelectSutehaiIdx = 100;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			if (state != PlayerAction.STATE_SUTEHAI_SELECT) {
				return true;
			}
			if (m_iSelectSutehai > jyunTehaiLength) {
				m_iSelectSutehai = jyunTehaiLength;
			}
			m_iSelectSutehai--;
			if (m_iSelectSutehai < 0) {
				m_iSelectSutehai = jyunTehaiLength;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			if (state != PlayerAction.STATE_SUTEHAI_SELECT) {
				return true;
			}
			m_iSelectSutehai++;
			if (m_iSelectSutehai > jyunTehaiLength) {
				m_iSelectSutehai = 0;
			}
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			m_isValidChiiLeft = m_isValidChiiCenter = m_isValidChiiRight = false;
			synchronized (m_drawItem) {
				switch (m_drawItem.m_state) {
				case STATE_PLAY:
					m_playerAction.setSutehaiIdx(m_iSelectSutehai);
					m_playerAction.actionNotifyAll();
					break;
				default:
					m_playerAction.actionNotifyAll();
					break;
				}
			}

			break;
		default:
			return super.onKeyDown(keyCode, event);
		}
		if (m_drawItem.m_isManReach) {
			m_iSelectSutehai = jyunTehaiLength;
		}
		invalidate();
		return true;
	}

	/** �̔v�̃C���f�b�N�X */
	private int mSutehaiIdx = 0;
	@Override
	public String getName() {
		return mName;
	}

    //private int mSkipIdx = 0;

    /** �i�s�̑҂����� */
	//private static int PROGRESS_WAIT_TIME = 100;
	private static int PROGRESS_WAIT_TIME = 30;

	/**
	 * �C�x���g����������B
	 *
	 * @param a_kazeFrom
	 *            �C�x���g�𔭍s������
	 * @param a_kazeTo
	 *            �C�x���g�̑Ώۂ̉�
	 * @param a_eventId
	 *            �C�x���gID
	 */
	@Override
	public EventId event(EventId a_eventId, int a_kazeFrom, int a_kazeTo) {
		m_drawItem.m_eventId = a_eventId;
		m_drawItem.m_kazeFrom = a_kazeFrom;
		m_drawItem.m_kazeTo = a_kazeTo;

		Log.d("UI", "a_eventId = " + a_eventId.toString() + ", a_kazeFrom = " + a_kazeFrom + ", a_kazeTo = " + a_kazeTo);
		switch (a_eventId) {
		case UI_WAIT_PROGRESS:// �i�s�҂�
			try {
				Thread.sleep(PROGRESS_WAIT_TIME, 0);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
			break;
		case START_GAME:// �e����
			// ���̒i�K�ŏ���������Ă������ݒ肷��B
			m_drawItem.setState(STATE_NONE);
			synchronized (m_drawItem) {
				// �_�_��ݒ肷��B
				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
					m_drawItem.m_playerInfos[i].m_tenbo = m_infoUi.getTenbou(i);
				}

				// ���[�`�_�̐���ݒ肷��B
				m_drawItem.setReachbou(m_infoUi.getReachbou());

				// �{���ݒ肷��B
				m_drawItem.setHonba(m_infoUi.getHonba());

				// �N�Ƃ�ݒ肷��B
				m_drawItem.setChiicha(m_infoUi.getChiichaIdx());

				m_drawItem.m_tsumoRemain = 0;
			}
			break;
		case END_GAME:
			synchronized (m_drawItem) {
				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
					m_drawItem.m_playerInfos[i].m_tenbo = m_infoUi.getTenbou(i);
				}
				m_drawItem.setHonba(m_infoUi.getHonba());
				m_drawItem.setReachbou(m_infoUi.getReachbou());
			}
			m_drawItem.setState(STATE_END);
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case START_KYOKU:// �T�C�U��
			// �T�C�U����ǂ̊J�n�ƍl����B
			m_drawItem.m_isManReach = false;

			// �ǂ̕������ݒ肷��B
			m_drawItem.setKyokuString(getResources(), m_infoUi.getkyoku());

			m_drawItem.setState(STATE_KYOKU_START);

			// �`�悷��B
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// �A�N�V������҂B
			m_playerAction.actionWait();
//			break;
//		case HAIPAI:
			// ��v��ݒ肵�āA�v���C��Ԃɂ���B
			m_drawItem.setState(STATE_PLAY);
			synchronized (m_drawItem) {
				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
					m_infoUi.copyTehai(m_drawItem.m_playerInfos[i].m_tehai, i);
					m_infoUi.copyKawa(m_drawItem.m_playerInfos[i].m_kawa, i);
					m_drawItem.m_playerInfos[i].m_tsumoHai = null;
					m_drawItem.m_playerInfos[i].m_tenbo = m_infoUi.getTenbou(i);
				}
				m_drawItem.setHonba(m_infoUi.getHonba());
				m_drawItem.setReachbou(m_infoUi.getReachbou());
				m_drawItem.m_tsumoRemain = m_infoUi.getTsumoRemain();
			}

			// �`�悷��B
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case RYUUKYOKU:// ����
			boolean tenpai[] = m_infoUi.getTenpai();
			synchronized (m_drawItem) {
				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
					m_drawItem.m_playerInfos[i].m_tenpai = tenpai[i];
					m_drawItem.m_playerInfos[i].m_tenbo = m_infoUi.getTenbou(i);
				}
			}

			// �T�C�U����ǂ̊J�n�ƍl����B
			m_drawItem.setState(STATE_RYUUKYOKU);

			// �`�悷��B
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// �A�N�V������҂B
			m_playerAction.actionWait();

			synchronized (m_drawItem) {
				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
					m_drawItem.m_playerInfos[i].m_tenpai = false;
				}
			}
			break;
		case NAGASHI:// ����
			// �����\�����Ȃ��B
			break;
		case TSUMO:// �c��
			synchronized (m_drawItem) {
				// �c���v���擾����B
				m_drawItem.m_playerInfos[m_infoUi.getJikaze()].m_tsumoHai = m_infoUi.getTsumoHai();

				m_drawItem.m_tsumoRemain = m_infoUi.getTsumoRemain();
			}

			// �`�悷��B
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case TSUMO_AGARI:// �c��������
			m_drawItem.setState(STATE_TSUMO);
			m_infoUi.copyTehai(m_drawItem.m_playerInfos[a_kazeFrom].m_tehai, a_kazeFrom);
			m_drawItem.m_playerInfos[a_kazeFrom].m_tsumoHai = m_infoUi.getTsumoHai();
			m_drawItem.m_playerInfos[a_kazeFrom].m_tenpai = true;

			// �`�悷��B
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// �A�N�V������҂B
			m_playerAction.actionWait();

			// ���ʉ�ʂ�\������B
			m_drawItem.m_state = STATE_RESULT;

			m_drawItem.m_playerInfos[a_kazeFrom].m_tenpai = false;

			// �`�悷��B
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// �A�N�V������҂B
			m_playerAction.actionWait();
			break;
		case SUTEHAI:// �̔v
			// ��v���R�s�[����B
			m_infoUi.copyTehai(m_drawItem.m_playerInfos[a_kazeFrom].m_tehai, a_kazeFrom);

			// �͂��R�s�[����B
			m_infoUi.copyKawa(m_drawItem.m_playerInfos[a_kazeFrom].m_kawa, a_kazeFrom);

			// �c���v���Ȃ����B
			m_drawItem.m_playerInfos[a_kazeFrom].m_tsumoHai = null;

			// �`�悷��B
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case SELECT_SUTEHAI:
			if (a_kazeFrom == m_infoUi.getJikaze()) {
				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
					m_infoUi.copyTehai(m_drawItem.m_playerInfos[i].m_tehai, i);
				}
					//mDrawItem.tsumoKaze = 5;
					//mDrawItem.tsumoHai = null;
					//mDrawItem.tsumoHais[mInfoUi.getJikaze()] = null;
					this.postInvalidate(0, 0, getWidth(), getHeight());
			}
			break;
		case UI_WAIT_RIHAI:
			m_drawItem.setSkipIdx(m_infoUi.getSutehaiIdx());
			m_drawItem.m_state = STATE_RIHAI_WAIT;
			this.postInvalidate(0, 0, getWidth(), getHeight());
			try {
				Thread.sleep(PROGRESS_WAIT_TIME, 0);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
			m_iSelectSutehai = 13;
			m_drawItem.m_state = STATE_PLAY;
			break;
		case PON:// �|��
		case CHII_LEFT:
		case CHII_CENTER:
		case CHII_RIGHT:
		case KAN:
		case ANKAN:
		case DAIMINKAN:
			// �����̎̔v�݂̂�\�����܂��B
			if (a_kazeFrom == m_infoUi.getJikaze()) {
				{
					for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
						m_infoUi.copyTehai(m_drawItem.m_playerInfos[i].m_tehai, i);
						m_infoUi.copyKawa(m_drawItem.m_playerInfos[i].m_kawa, i);
					}
					// mDrawItem.tsumoKaze = 5;
					// mDrawItem.tsumoHai = null;
					//m_drawItem.m_playerInfos[m_infoUi.getJikaze()] = null;
					m_iSelectSutehai = 0;
					this.postInvalidate(0, 0, getWidth(), getHeight());
				}
			}
			break;
		case REACH:
			synchronized (m_drawItem) {
				for (int i = 0; i < m_drawItem.m_playerInfos.length; i++) {
					m_drawItem.m_playerInfos[i].m_tenbo = m_infoUi.getTenbou(i);
				}
				m_drawItem.setReachbou(m_infoUi.getReachbou());
			}

			// ��v���R�s�[����B
			m_infoUi.copyTehai(m_drawItem.m_playerInfos[a_kazeFrom].m_tehai, a_kazeFrom);

			// �͂��R�s�[����B
			m_infoUi.copyKawa(m_drawItem.m_playerInfos[a_kazeFrom].m_kawa, a_kazeFrom);

			// �c���v���Ȃ����B
			m_drawItem.m_playerInfos[a_kazeFrom].m_tsumoHai = null;

			if (m_infoUi.getManKaze() == a_kazeFrom) {
				m_drawItem.m_isManReach = true;
				//mSelectSutehaiIdx = m_playerAction.m_indexs[0];
			}
			m_drawItem.m_state = STATE_REACH;
			this.postInvalidate(0, 0, getWidth(), getHeight());
			try {
				Thread.sleep(1000, 0);
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
			m_drawItem.m_state = STATE_PLAY;
			// �`�悷��B
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		case RON_AGARI:// ����
			// ��v���R�s�[����B
			m_infoUi.copyTehai(m_drawItem.m_playerInfos[a_kazeFrom].m_tehai, a_kazeFrom);
			m_drawItem.m_suteHai = m_infoUi.getSuteHai();
			m_drawItem.m_playerInfos[a_kazeFrom].m_tenpai = true;

			m_drawItem.m_state = STATE_RON;
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// �A�N�V������҂B
			m_playerAction.actionWait();

			// ���ʉ�ʂ�\������B
			m_drawItem.m_state = STATE_RESULT;

			m_drawItem.m_playerInfos[a_kazeFrom].m_tenpai = false;

			// �`�悷��B
			this.postInvalidate(0, 0, getWidth(), getHeight());

			// �A�N�V������҂B
			m_playerAction.actionWait();
		case UI_INPUT_PLAYER_ACTION:
			this.postInvalidate(0, 0, getWidth(), getHeight());
			break;
		default:
			break;
		}

		return EventId.NAGASHI;
	}
    @Override
	public int getISutehai() {
		return mSutehaiIdx;
	}
}