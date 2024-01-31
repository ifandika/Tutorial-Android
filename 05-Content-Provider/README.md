# Content Provider
```java
public class MyContentProvider extends ContentProvider {
	
	@Override
	public void onCreate() {
		
	}
	...
}
```
- Digunakan untuk __mengakses data, menyimpan data apk, pusat untuk data apk. Berkerja di data akses layer.__
## Alur
![Alur Content Provider](/ic_flow_contentprovider.jpg)
### Content URI(Uniform Resource Identifier)
- Konsep utama pada content provider.
```
content://pemilik/path(opsional)/path_id(opsional)
```
- __content__ = Standar alamat sumber.
- __pemilik__ = Nama pemilik data yg akan dituju.
- __path__ = Untuk membedakan jenis data yg di tuju, semisal data gambar pd folder gambar, video pd folder video.
- __path_id__ = Jika ingin mengakses data tertentu, semisal akses data gambar ke ..., video ke ... .
## Operasi dasar
- [x] __CREATE__
- [x] __READ__ 
- [x] __UPDATE__
- [x] __DELETE__
## Alur akses data.
![Alur Content Provider](/ic_flow_access_data.jpg)
## Catatan
- __ContentResolver__ untuk
- __ContentProvider__ untuk
- __Cursor__ untuk kueri ke db sekaligus menyimpan hasil kueri dan mengambil data yg disimpannya.