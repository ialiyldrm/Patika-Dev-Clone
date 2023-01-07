# Patika.Dev Clone

**Bootcamp Hızlandırma Programı-Java** kampsamında **Java-102** eğitiminin tamamlama projesidir.

- Proje, Model / View / Controller(Model-Görünüm-Kontrol) tasarım şablonuna dayalı oluşturulmuştur.
- Java Swing kullanılmıştır.
- PostgreSql database kullanılmıştır.

## Model

### User

User sınıfı, bir kullanıcının bilgilerini tutan bir Java sınıfıdır. 
Bu sınıfta, bir kullanıcının ID'si, adı, kullanıcı adı, şifresi ve tipi gibi özellikleri saklanır. 
Ayrıca, User sınıfının içinde birkaç metod bulunur. Bu metodlar arasında, **getList**() metodu tüm kullanıcıların listesini döndürür, 
**add**() metodu veritabanına yeni bir kullanıcı ekler, **getFetch**() metodu veritabanından belirtilen kullanıcı adına sahip 
bir kullanıcıyı döndürür ve **delete**() metodu veritabanından belirtilen kullanıcıyı siler.

**Operator**,**Student**,**Educator** User sınıfından kalıtılır.

### Course

**Course** sınıfı, derslerin modellendiği sınıftır. Bu sınıfta, derslerle ilgili veritabanı işlemleri yapılır. 
Veritabanındaki derslerle ilgili bilgiler, ders adı, dersin dilini içeren veritabanındaki "**course**" tablosu üzerinden yönetilir.

* **id**: Dersin veritabanındaki id'si.
* **user_id**: Dersi veren kullanıcının veritabanındaki id'si.
* **patika_id**: Dersin ait olduğu patikaya veritabanındaki id'si.
* **name**: Dersin adı.
* **language**: Dersin dilinin adı.
* **patika**: Dersin ait olduğu patika nesnesi.
* **educator**: Dersi veren kullanıcı nesnesi.

Course sınıfında, derslerle ilgili önemli metotlar bulunur. Bunlar;

* **getList()**: Veritabanındaki tüm dersleri geri döndürür.
* **add(int user_id, int patika_id, String name, String language)**: Veritabanına yeni bir ders ekler.
* **update(int id, String name)**: Veritabanındaki bir dersin adını günceller.
* **delete(int id)**: Veritabanındaki bir dersi siler.

### Patika

Patika sınıfı, öğrencilerin okuduğu eğitimlerin kategorilerini tutan bir sınıftır.

**id**: Patika nesnesinin veritabanında tutulan kimlik numarası.
**name**: Patika nesnesinin adı.

* **getList**(): Veritabanında kayıtlı tüm patika nesnelerinin bir listesi döndürür.
* **add**(String): Veritabanına yeni bir patika nesnesi ekler.
* **update**(int, String): Veritabanında bulunan bir patika nesnesinin adını günceller.
* **getFetch**(int): Veritabanında belirtilen kimlik numarasına sahip bir patika nesnesini döndürür.
* **delete**(int): Veritabanında belirtilen kimlik numarasına sahip bir patika nesnesini siler.

### Education

**Education** sınıfı, eğitim bilgilerini tutan bir Java sınıfıdır. Eğitimlerin isimleri, idsi, eğitmen id'si, ders id'si,
youtube linki, eğitim açıklaması ve quiz bilgilerini saklar. Ayrıca eğitmeni ve dersi gösteren ilişki sınıflarına da sahiptir.

**name** (String): Eğitimin adı
**id** (int): Eğitimin veritabanındaki id değeri
**user_id** (int): Eğitimi yaratan kullanıcının veritabanındaki id değeri
**course_id** (int): Eğitimin ait olduğu dersin veritabanındaki id değeri
**url** (String): Eğitimin YouTube videonun bağlantısı
**comment** (String): Eğitim hakkında açıklama
**quiz** (String): Eğitimle ilgili quizin linki
**course** (Course): Eğitimin ait olduğu ders
**user** (User): Eğitimi yaratan kullanıcı

* **getList**(): Tüm eğitimleri döndürür.

* **getListByUserId**(int): Belirtilen kullanıcının yaratmış olduğu tüm eğitimleri döndürür.

* **add**(int, int, String, String): Veritabanına yeni bir eğitim ekler.

* **update**(int, String, String, String, String): Eğitim tablosundaki verileri günceller. Bu metot, eğitimin id'si, adı, url'si, yorumu ve quiz'i parametre olarak alır.

* **getFetch**(int): Eğitim tablosundan belirtilen id'ye sahip bir eğitim nesnesi döndürür.

* **delete**(int): Eğitim tablosundan belirtilen id'ye sahip bir eğitim verisini siler.

* **getList**(): Eğitim tablosundaki tüm verileri Education nesnelerine dönüştürerek bir ArrayList olarak döndürür.

* **getListByUserId**(int): Belirtilen kullanıcı id'sine sahip eğitim verilerini Education nesnelerine dönüştürerek bir ArrayList olarak döndürür.

* **getListByCourseId**(int): Belirtilen kurs id'sine sahip eğitim verilerini Education nesnelerine dönüştürerek bir ArrayList olarak döndürür.

* **getListByPatikaId**(int): Belirtilen patika id'sine sahip eğitim verilerini Education nesnelerine dönüştürerek bir ArrayList olarak döndürür.


## View

### LoginGUI

LoginGUI sınıfı, kullanıcı girişi yapılabilecek bir pencere oluşturur. Bu sınıf, **fld_user_uname** ve **fld_user_pass** adında 
iki metin alanı ve **btn_login** ve **btn_register** adında iki düğme içerir. btn_login düğmesine tıklandığında, verilen kullanıcı 
adı ve parola ile veritabanında bir kullanıcı aranır. Eğer kullanıcı bulunursa, kullanıcı türüne göre ilgili pencereyi açar 
ve giriş penceresini kapatır. Eğer kullanıcı bulunamazsa, kullanıcıya "Kullanıcı Bulunamadı!" mesajı gösterir. 
btn_register düğmesine tıklandığında ise, giriş penceresi kapatılır ve yeni bir kullanıcı kaydı yapılabilecek bir pencere açılır.

### RegisterGUI

**RegisterGUI** sınıfı, bir **JFrame** sınıfından türer ve üyelik oluşturmak için kullanıcı adı, şifre, ad soyad ve üyelik tipi bilgilerini alan bir arayüz oluşturur. 
Bu sınıfta, **fld_user_name**, **fld_user_uname**, **fld_user_pass** ve **cmb_user_type** adında dört tane JTextField ve JComboBox nesnesi, 
**btn_user_add** adında bir JButton nesnesi ve pnl_top ve pnl_bottom adında iki tane JPanel nesnesi tanımlanır.

**btn_user_add**'e bir **ActionListener** eklenir ve bu dinleyici, eğer fld_user_name, fld_user_uname ve fld_user_pass 
alanları boş ise kullanıcıya "**fill**" mesajı gösterir. Eğer bu alanlar dolu ise, kullanıcı adı, şifre, ad soyad ve üyelik tipi bilgilerini alır
ve **User** sınıfının **add()** metodunu çağırarak veritabanına bu bilgileri ekler. Eğer ekleme işlemi başarılı ise,
"**done**" mesajı gösterir ve girdiği bilgileri temizler. Daha sonra, **RegisterGUI** penceresi kapatılır ve **LoginGUI** penceresi açılır.

### EducatorGUI

**EducatorGUI** sınıfı, bir eğitimci kullanıcısının eğitimlerini yönetebileceği bir pencere oluşturur. 
Bu sınıf, eğitimleri ekleyebileceği bir form, eğitimlerin listesini görüntüleyebileceği bir tablo, eğitimleri düzenleyebileceği 
ve silebileceği bir form, dersleri listeleyebileceği bir tablo ve dersleri ekleyebileceği bir form içerir. 
Eğitimler ve dersler ile ilgili tüm işlemler, veritabanına gönderilen SQL sorguları aracılığıyla gerçekleştirilir. 
Eğitimci kullanıcısı giriş yaptıktan sonra bu pencere açılır ve kullanıcı eğitimleri ve dersleri yönetebilir. 
Ayrıca, kullanıcının giriş yaptığı bilgi de bu pencerenin en üst kısmında **"Hoşgeldin [Kullanıcı Adı]"** şeklinde gösterilir 
ve kullanıcı çıkış yapmak için **"Çıkış Yap"** düğmesini kullanabilir.

### OperatorGUI

OperatorGUI sınıfı, Operator tipinde bir operator nesnesini parametre olarak alır ve bu nesnenin bilgilerini kullanır. 
Bu sınıf, bir JFrame sınıfından türer ve çeşitli **JPanel**, **JScrollPane**, **JTable**, **JLabel**, **JTextField**, 
**JComboBox** ve **JButton** nesnelerini kullanarak bir arayüz oluşturur. 
Bu arayüz, operatorların kullanıcı, patika, ders ve eğitim bilgilerini yönetmelerine yardımcı olur.

OperatorGUI sınıfının içinde, ayrıca tüm veritabanı işlemlerini yapabilmek için **DefaultTableModel** türünde 
**mdl_user_list**, **mdl_patika_list**, **mdl_course_list** ve **mdl_education_list** adında dört tane model tanımlanır. 
Bu modeller, veritabanındaki bilgileri tutar ve bu bilgileri JTable nesnelerine gösterir.

OperatorGUI sınıfında ayrıca, JPopupMenu türünde bir **patikaMenu** nesnesi tanımlanır. 
Bu nesne, **tbl_patikaList** **JTable**'ında sağ tıklama yaptığımızda açılan bir **menüdür**. 
Bu menüde iki seçenek bulunur: "**Güncelle**" ve "**Sil**". "Güncelle" seçeneği tıklandığında, patika bilgilerini güncellemek için 
**UpdatePatikaGUI** sınıfı açılır. "**Sil**" seçeneği tıklandığında ise, patika veritabanından silinir.

OperatorGUI sınıfının büyük bir kısmı, veritabanındaki bilgileri göstermek ve düzenlemek için JTable nesnelerine model ataması, 
bu modelleri doldurmak için veritabanı sorguları yapılması, veritabanındaki bilgilerin güncellenmesi ve silinmesi için **listener** metodlarının tanımlanmasından oluşur.

OperatorGUI sınıfının diğer önemli özellikleri arasında, **kullanıcı ekleme**, **patika ekleme** ve **ders ekleme** işlemlerinin yapılabilmesi sayılabilir. 
Bu işlemler için **JTextField** ve **JComboBox** nesnelerinden oluşan formlar bulunur. Bu formlarda girilen bilgiler veritabanına eklenir.

Son olarak, OperatorGUI sınıfında, **tbl_educationList** JTable nesnesi için de aynı şekilde model tanımlaması, 
veritabanı sorguları yapılarak model doldurma işlemleri ve veritabanındaki bilgilerin güncellenmesi ve silinmesi için listener metodları tanımlanır.

### StudentGUI

StudentGUI sınıfı, **Student** tipinde bir student nesnesini parametre olarak alır ve bu nesnenin bilgilerini kullanır. 
Bu sınıf, bir **JFrame** sınıfından türer ve çeşitli **JPanel**, **JScrollPane**, **JTable** ve **JLabel** nesnelerini kullanarak bir arayüz oluşturur. 
Bu arayüz, öğrencilerin eğitimleri listeleyebilmelerine yardımcı olur.

StudentGUI sınıfının içinde, ayrıca tüm veritabanı işlemlerini yapabilmek için **DefaultTableModel** türünde **mdl_education_list** adında bir model tanımlanır.
Bu model, veritabanındaki eğitim bilgilerini tutar ve bu bilgileri JTable nesnesine gösterir.

StudentGUI sınıfında ayrıca, öğrencilerin oturumlarını sonlandırabilmeleri için **btn_logout** adında bir JButton nesnesi tanımlanır. 
Bu nesnenin **ActionListener**'ı, öğrenciyi kullandığı JFrame'i kapatır ve yeni bir **LoginGUI** nesnesi oluşturur. 
Bu sayede, öğrenci oturumunu sonlandırır ve yeniden giriş yapabilir.

StudentGUI sınıfında, loadEducationModel() adında bir metod da tanımlanmıştır. Bu metod, veritabanından eğitimleri getirir ve mdl_education_list modeline ekler. Böylece, JTable nesnesine bu bilgiler gösterilebilir ve öğrenci eğitimlerini listeleyebilir.

### UpdatePatikaGUI

UpdatePatikaGUI sınıfı, **Patika** tipinde bir patika nesnesini parametre olarak alır ve bu nesnenin bilgilerini kullanır. 
Bu sınıf, bir **JFrame** sınıfından türer ve çeşitli **JPanel**, **JTextField** ve **JButton** nesnelerini kullanarak bir arayüz oluşturur. 
Bu arayüz, patika bilgilerini güncelleyebilme veya silebilme işlemlerini yapmaya yardımcı olur.

UpdatePatikaGUI sınıfında, patika bilgilerini güncellemek için **fld_patika_name** adında bir **JTextField** nesnesi ve **btn_update** adında bir JButton nesnesi tanımlanır.
Kullanıcı bu nesneler aracılığıyla patika adını güncelleyebilir ve bu güncellemeyi tamamlamak için **btn_update**'e tıklayabilir.

UpdatePatikaGUI sınıfında ayrıca, patika bilgilerini silebilme işlemi için **btn_delete** adında bir JButton nesnesi tanımlanır.
Kullanıcı bu nesneye tıkladığında patika bilgileri veritabanından silinir.

## Helper 

Helper package, uygulamada kullanılabilecek ortak işlevleri içeren sınıfları içerir. Bu package, aşağıdaki sınıfları içerir:

* **DBConnector**: Bu sınıf, veritabanına bağlantı sağlar. Bu sınıf, veritabanı url'sini, veritabanı kullanıcı adını ve 
veritabanı parolasını kullanarak veritabanına bağlantı sağlar.

* **Helper**: Bu sınıf, bir Swing uygulamasında kullanılabilecek ortak işlevleri içerir. Bu sınıf, uygulamanın arayüzüne 
göre tasarım seçeneği sağlar, ekranın ortasından bir pencere açar, bir pencerenin boş alanlarının olup olmadığını kontrol eder 
ve kullanıcıya mesajlar gösterir ve kullanıcıdan onay ister.

* **Item**: Bu sınıf, anahtar-değer ikilisi tutan bir veri yapısıdır. Bu sınıf, key ve value özelliklerine sahiptir ve 
bu özellikleri kullanarak anahtar-değer ikilisini tutar. Bu sınıf ayrıca, anahtar-değer ikilisinin string haliyle döndürülmesini sağlar.

* **Config**: Bu sınıf, uygulama için gerekli olan sabit değişkenleri tutar. Bu sınıf, uygulama başlığını, 
veritabanı url'sini, veritabanı kullanıcı adını ve veritabanı parolasını saklar.

## Patika.Dev

[Eğitim Linki](https://app.patika.dev/courses/java-102/patikaklon-1)
