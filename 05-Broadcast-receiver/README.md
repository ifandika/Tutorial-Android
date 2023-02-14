# Broadcast Receiver
```java
public class MyBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceiver(Context context, Intent intent) {
		...
	}
}
```
- Untuk merespon jika terjadi suatu tindakan dari sistem atau apk lain. Misalnya jika kita
 menghidupkan mode pesawat maka terjadi tindakan dari sistem maka BroadcastReceiver akan 
 merespon. Objek yang menerima suatu tindakan disebut Receiver.
	- Jenis:
		- Static(Statik) = Yg dideklarasi di file manifest dan berjalan walau apk telah berhenti.
		- Dynamic(Dinamis) = Di deklarasi di file program, berjalan ketika apk aktif/latar belakang,
							 untuk dinamis haru register dan unregister.
	- 2 Lankah yg diperlukan:
		- Membuat
		- Me register
### Cara Kerja
- Dinamis
	- Buat objek BR dan masukan ke "class" utama.
	- Registerkan objek BR dan IntentFilter(Jenis aksi²/tindakan).
	- Di "class" BR nya akan menagkap semua tindakan dalam bentuk Intent.
	- Ambil jenis aksi dari Intent dan seterusnya.
	- Jangan lupa hapus registrasi objek BR.
- Statik
	- Buat objek BR 
	- Jenis² aksi/IntentFilter di deklarasikan di file manifest dan dalam elemen receiver
### Demo
- Kode MainActivity
```java
	...
	private BroadcastReceiver myBR;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Tetapkan nilai objek.
		myBR = new MyBroadcastReceiver();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		// Objek IntentFilter dengan tipe aksi tindakan dari Bluetooth
		IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		// Register objek BR dan IntentFilter
		getApplicationContext().registerReceiver(myBR, intentFilter);
		Toast.makeText(getApplicationContext(), "Sukses register BR", Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		// Hapus register objek BR
		getApplicationContext().unregisterReceiver(myBR);
		Toast.makeText(getApplicationContext(), "Sukses unregister BR", Toast.LENGTH_LONG).show();
	}
	...
```
- Kode MyBroadcastReceiver
```java
	...
	@Override
	public void onReceive(Context context, Intent intent) {
		// Dapatkan aksi
		String action = intent.getAction();
		
		// Cek jika aksi sama dengan yg kita inginkan
		if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
		// Dapatkan kode dari setiap tindakan dari Bluetooth
		final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
		switch(state) {
			// Jika Bluetooth dihidupkan
			case BluetoothAdapter.STATE_ON: 
				Toast.makeText(context, "Bluetooth on", Toast.LENGTH_LONG).show();
				break;
			// Jika Bluetooth dimatikan
			case BluetoothAdapter.STATE_OFF:
				Toast.makeText(context, "Bluetooth off", Toast.LENGTH_LONG).show();
				break;
		}
	}
	...
```
- Kode Manifest
```java
	...
	<uses-permission android:name="android.permission.BLUETOOTH"/>
	
	<activity
		android:name=".MainActivity"
		android:label="@string/app_name">
		<intent-filter>
			<action android:name="android.intent.action.MAIN"/>
			<category android:name="android.intent.category.LAUNCHER"/>
			</intent-filter>
	</activity>
	
	<receiver
		android:name=".MyBroadcastReceiver">
	</receiver>
	...
```
### Catatan
- Agar Broadcast Receiver aman tetapkan android:exported=false
- Jangan berikan informasi sensitif pada "broadcast", informasi dapat dibaca apk lain yg
 meregister untuk menerima "broadcast" tersebut. Mengirim secara spesifik dengan fungsi setPackage().
- Fungsi onReceive() dijalankan di thread utama.
- Jangan mulai aktivitas apapaun dari broadcast receiver.