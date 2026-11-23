# Лаборатори 4 — JUnit 5

Нэр: Ган-Оргил  
Оюутны код: B232270091

F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест

## Ажиллуулсан орчин

`java -version`

```text
openjdk version "25.0.4.1" 2026-08-18 LTS
OpenJDK Runtime Environment Temurin-25.0.4.1+1 (build 25.0.4.1+1-LTS)
OpenJDK 64-Bit Server VM Temurin-25.0.4.1+1 (build 25.0.4.1+1-LTS, mixed mode, sharing)
```

`mvn -version`

```text
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: C:\Users\enhta\Desktop\lab2\.tools\apache-maven-3.9.9
Java version: 25.0.4.1, vendor: Eclipse Adoptium, runtime: C:\Program Files\Eclipse Adoptium\jdk-25.0.4.101-hotspot
Default locale: en_US, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

## Ажиллуулах

Онооноос үсгэн дүн гаргах, үнэлгээний хэсгүүдийн нийлбэрийг тооцох классыг JUnit 5-аар шалгах Maven төсөл.

Java 17 буюу түүнээс дээш хувилбар болон Maven шаардлагатай.

```sh
mvn test
```

## Тестийн үр дүн

Нийт **16 тестийн метод** бичсэн: 11 нь `@Test`, 5 нь `@ParameterizedTest`.
Parameterized тестийн мөр бүр тусдаа ажилладаг учраас Maven-ийн `Tests run` тоо **44** гарсан.

| Ажиллуулалт | Tests run | Failures | Errors | Skipped | Үр дүн |
| --- | ---: | ---: | ---: | ---: | --- |
| Зөв хэрэгжүүлэлт | 44 | 0 | 0 | 0 | BUILD SUCCESS |
| `>= 90` → `> 90` мутаци | 44 | 2 | 0 | 0 | BUILD FAILURE |
| Нөхцөлийг буцааж зассаны дараа | 44 | 0 | 0 | 0 | BUILD SUCCESS |

Эцсийн ногоон гаралт [mvn-test.txt](results/mvn-test.txt),
мутацийн гаралт [mvn-test-mutant.txt](results/mvn-test-mutant.txt) файлд байгаа.

Мутацид `ninetyIsExactlyA` болон `letterGradeBoundaries`-ийн `90,A` мөр унасан.
Хоёуланд нь `expected: <A> but was: <B>` гэсэн алдаа гарсан.
Одоогийн кодод `score >= 90` нөхцөлийг буцааж сэргээсэн.

## Хийсэн ажлын тайлбар

`GradeCalculator` класст нийт онооноос үсгэн дүн гаргах болон үнэлгээний таван хэсгийн нийлбэрийг олох хоёр метод бичлээ.
Оноог тоймлохгүйгээр шалгасан тул 89.99 нь B, харин яг 90 нь A болно.
Тестүүдээ өгөгдөл бэлдэх, үйлдэл хийх, үр дүнг шалгах гэсэн AAA дарааллаар зохион байгуулж, монгол нэр өгсөн.
Parameterized тестээр дүн солигдох заагууд болон хэсэг бүрийн сөрөг, дээд хязгаараас давсан утгуудыг шалгалаа.
Бутархай онооны нийлбэрийг шалгахдаа тооцооллын өчүүхэн зөрүүг зөвшөөрөхөөр `0.000001` нарийвчлал ашигласан.
Хамгийн сонирхолтой нь `ninetyIsExactlyA` тест байсан: `>=` тэмдгийг `>` болгоход 95 онооны ердийн шалгалт өнгөрсөн ч яг 90 онооны шалгалт алдааг илрүүлсэн.
Нөхцөлийг буцааж засаад бүх 44 шалгалт ногоон болсныг хадгалсан нь зөвхөн тест амжилттай ажиллахаас гадна зориудын алдааг барьж байгааг харууллаа.

## Логийг дахин гаргах

Linux эсвэл macOS:

```sh
mkdir -p results
mvn test 2>&1 | tee results/mvn-test.txt
```

Windows PowerShell:

```powershell
New-Item -ItemType Directory -Force results | Out-Null
mvn test 2>&1 | Out-File -Encoding utf8 results/mvn-test.txt
$LASTEXITCODE
```

Мутацийг давтахдаа `GradeCalculator.java` дахь `score >= 90`-ийг `score > 90` болгож,
гаралтаа `results/mvn-test-mutant.txt` нэрээр хадгална.
Дараа нь нөхцөлийг заавал буцааж засаад ногоон логийг дахин гаргана.

JUnit 5-ийн annotation болон assertion-уудын тайлбарыг
[албан ёсны гарын авлагаас](https://docs.junit.org/5.10.2/user-guide/) харж болно.
