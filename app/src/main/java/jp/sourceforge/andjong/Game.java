package jp.sourceforge.andjong;

import jp.sourceforge.andjong.mahjong.Mahjong;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Game extends Activity {
	private static final String TAG = "Andjong";

	private AndjongView mAndjongView;

	private Mahjong mMahjong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		// �^�C�g����\�����Ȃ��悤�ɂ���B
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// �t���X�N���[���ɂ���B
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// View���쐬����B
		System.out.println("高度："+Utils.getScreenHeight(this)+"宽度："+Utils.getScreenWidth(this));
		mAndjongView = new AndjongView(this);
		setContentView(mAndjongView);
		mAndjongView.requestFocus();

		// �Q�[�����J�n����B
		mMahjong = new Mahjong(mAndjongView);
		new Thread(mMahjong).start();
	}
}
