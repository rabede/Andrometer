package de.bellweb.andrometer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText strom;
	private EditText gas;
	private EditText wasser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button saveButton = (Button) findViewById(R.id.buttonSave);

		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				strom = (EditText) findViewById(R.id.editStrom);
				gas = (EditText) findViewById(R.id.editGas);
				wasser = (EditText) findViewById(R.id.editWasser);

				String[] params = { strom.getText().toString(),
						gas.getText().toString(), wasser.getText().toString() };
				new GoogleForm().execute(params);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
