package de.bellweb.andrometer;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,
		OnFocusChangeListener {

	private final static String TAG = MainActivity.class.getSimpleName();

	private float mWater;
	private float mGas;
	private float mPower;
	private String mDate;
	private String mHistory;

	private DateFormat mDf;

	private EditText meTWater;
	private EditText meTGas;
	private EditText meTPower;
	private TextView mtVDate;
	private TextView mtVHistory;

	private Button mSaveButton;
	private Button mClearButton;

	private SharedPreferences mPrefs;

	private final static String KEY_WATER = "water";
	private final static String KEY_GAS = "gas";
	private final static String KEY_POWER = "power";
	private final static String KEY_HISTORY = "history";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate started");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get the Views into fields:
		meTWater = (EditText) findViewById(R.id.eTWater);
		meTGas = (EditText) findViewById(R.id.eTGas);
		meTPower = (EditText) findViewById(R.id.eTPower);
		mtVDate = (TextView) findViewById(R.id.tVDate);
		mtVHistory = (TextView) findViewById(R.id.tVHistory);

		mSaveButton = (Button) findViewById(R.id.btSave);
		mClearButton = (Button) findViewById(R.id.btClear);

		// clear Fields when focused:
		meTWater.setOnFocusChangeListener(this);
		meTGas.setOnFocusChangeListener(this);
		meTPower.setOnFocusChangeListener(this);

		// set onClickListerner for button:
		mSaveButton.setOnClickListener(this);
		mClearButton.setOnClickListener(this);

		// get values saved in last session:
		getSavedValues();

		// ...and set them to the corresponding fields:
		setTextViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Log.d(TAG, "onClick " + v.toString());

		mWater = getContent(meTWater);
		mPower = getContent(meTPower);
		mGas = getContent(meTGas);

		if (v.equals(mSaveButton)) {
			saveValues();
		} else if (v.equals(mClearButton)) {
			clearValues();
		}
	}

	private float getContent(EditText et) {
		// if field is cleared, parseFloat doesn't work:
		try {
			return Float.parseFloat(et.getText().toString());
		} catch (NumberFormatException e) {
			return 0f;
		}
	}

	private void setTextViews() {
		Log.d(TAG, "setTexts");
		meTWater.setText("" + mWater);
		meTGas.setText("" + mGas);
		meTPower.setText("" + mPower);
		mtVDate.setText(mDate);
		mtVHistory.setText(mHistory);
	}

	private void saveValues() {
		mHistory = mDate + "\n" + mWater + "\t" + mGas + "\t" + mPower + "\n\n"
				+ mHistory;
		Editor editor = mPrefs.edit();
		editor.putString(KEY_HISTORY, mHistory);
		editor.commit();
		Log.d(TAG, "Values saved");
		
		String[] params = { Float.toString(mPower), Float.toString(mGas), Float.toString(mWater) };
		new GoogleForm().execute(params);
		
		clearValues();
		setTextViews();

	}

	private void clearValues() {
		mWater = 0f;
		mPower = 0f;
		mGas = 0f;
		mDate = mDf.format(new Date());
		setTextViews();
	}

	private void getSavedValues() {
		Log.d(TAG, "getSavedValues");
		mPrefs = getPreferences(MODE_PRIVATE);
		mWater = mPrefs.getInt(KEY_WATER, 0);
		mGas = mPrefs.getInt(KEY_GAS, 0);
		mPower = mPrefs.getInt(KEY_POWER, 0);
		mHistory = mPrefs.getString(KEY_HISTORY, "");

		mDf = SimpleDateFormat.getDateTimeInstance();
		mDate = mDf.format(new Date());
		Log.d(TAG, "water: " + mWater + " gas: " + mGas + " Power: " + mPower
				+ " Date " + mDate);
	}

	@Override
	public void onFocusChange(View view, boolean focus) {
		if (focus) {
			EditText t = (EditText) view;
			t.setText("");
		}
	}
}


//import android.app.Activity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//public class MainActivity extends Activity {
//
//	private EditText strom;
//	private EditText gas;
//	private EditText wasser;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		Button saveButton = (Button) findViewById(R.id.buttonSave);
//
//		saveButton.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				strom = (EditText) findViewById(R.id.editStrom);
//				gas = (EditText) findViewById(R.id.editGas);
//				wasser = (EditText) findViewById(R.id.editWasser);
//
//				String[] params = { strom.getText().toString(),
//						gas.getText().toString(), wasser.getText().toString() };
//				new GoogleForm().execute(params);
//			}
//		});
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}
//}
