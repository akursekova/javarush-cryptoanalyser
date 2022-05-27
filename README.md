# javarush-cryptoanalyser

## Краткое описание

Приложение осуществляет шифровку/расшифровку текста по заданному сдвигу в алфавите.


**Требования к заданию:**  
https://javarush-team.notion.site/javarush-team/Road-map-f0135a266781448081f797862abf00f4


**Приложение состоих их четырех основных модулей:**  
1. Шифровка текста по заданному сдвигу
2. Расшифровка текста по заданному сдвигу
3. Расшифровка текста методом brute force 
4. Расшифровка текста методом статистического анализа текста

## До запуска проекта:  
Приложение работает с аргументами командной строки.  
Входные данные, которые вводятся пользователем должны соответствовать заднному формату. 

***

**Для шифровки текста по заданному сдвигу:**

>encrypt {-input}={value} {-output}={value} {-shift}={value}

`-input` - путь к файлу с текстом для шифровки  
`-output` - путь к файлу, в котором будет храниться зашифрованный текст  
`-shift` - номер сдвига

**Пример:**  
>encrypt -input=Test\input1.txt -output=Test\result1.txt -shift=7

***

**Для расшифровки текста по заданному сдвигу:**

>decryptWithShift {-input}={value} {-output}={value} {-shift}={value}

`-input` - путь к файлу с текстом для расшифровки
`-output` - путь к файлу, в котором будет храниться расшифрованный текст
`-shift` - номер сдвига

**Пример:**
>decryptWithShift -input=Test\input2.txt -output=Test\result2.txt -shift=7

***

**Для расшифровки текста методом brute force:** 

>decryptWithBruteForce {-input}={value} {-output}={value} {-textToAnalyse}={value}

`-input` - путь к файлу с текстом для расшифровки
`-output` - путь к файлу, в котором будет храниться расшифрованный текст
`-textToAnalyse` - путь к файлу с текстом для анализа

**Пример:**
>decryptWithBruteForce -input=Test\input3.txt -output=Test\result3.txt -textToAnalyse=Test\toAnalyse.txt

***

**Для расшифровки текста методом статистического анализа текста:**

>decryptWithStatAnalysis {-input}={value} {-output}={value} {-textToAnalyse}={value}

`-input` - путь к файлу с зашифрованным текстом
`-output` - путь к файлу, где будет храниться расшифрованный текст
`-textToAnalyse` - путь к файлу для анализа 

**Пример:**
>decryptWithStatAnalysis -input=Test\input4.txt -output=Test\result4.txt -textToAnalyse=Test\toAnalyse.txt

# Запуск проекта:

>$ java -Dfile.encoding=UTF-8 -jar ./my-main-v-0.0.1.jar {operation} {arg1}={value} {arg2}={value} {arg3}={value}

**Важно:**
_Текстовые файлы, используемые для работы с приложением, должны находиться в одной папке с .jar для удобства пользователя. 
Тогда при указании путей к файлам достаточно будет указать их имена. 
Если файлы лежат отдельно от папки с .jar, необхдимо указывать полный путь к файлам._


# Краткое описание классов

В корневом пакете проекта `ru.javarush.akursekova.cryptoanalyser` находится класс Main, содержащий в себе точку входа в приложение.  

В пакете `input_data_processor` содержатся классы:  
* `InputDataValidator` - класс, проверяющий, что введенные пользователем вводные данные соответсвуют заданному формату.  
* `InputDataParser` - класс, который парсит ввыодные данные: преобьразует аргументы командной строки в объект четырьмя полями. Поля вариьируются в зависимости от модуля, который хочет запустить пользователь.  
* `OperationProcessor` - класс, который определяет какой модуль приложения запустить, основываясь на том, какую операция указал пользователь на входе.

`encrypt` - запустится модуль по шифровке текста по заданному сдвигу  
`decryptWithShift` - запустится модуль по расшифровке текста по заданному сдвигу  
`decryptWithBruteForce` - запустится модуль по расшифровке текста методом brute force  
`decryptWithStatAnalysis` - запустится модуль по расшифровке текста методом статистического анализа текста

В пакете `exception` содержится класс:
`FileProcessingException` - класс, отвечающий за обработку ошибок при работе с файлами.

В пакете `alphabet` содержится класс:
`Alphabet` - класс, который хранит набор символов, которые могут попасться в тексте, поддающемся шифровке/расшифровке.

В пакете `encrypt_shift` содержится класс:  
`TextEncryptor` - класс, описывающий логику шифровки текста по заданному сдвигу.

В пакете `decrypt_shift` содержится класс:  
`DecryptShift` - класс, описывающий логику расшифровки текста по заданному сдвигу.

В пакете `decrypt_brute_force` содержится два класса:
* `DecryptBruteForce` - класс, описывающий основную логику расшифровки текста методом brute force  
* `DictionaryGenerator` - вспомогательный класс, генерирующий словарь слов по предоставленному на вход тексту 

Класс `DecryptBruteForce` работает с двумя текстами: текст, который нужно расшифровать и текст, который предоставляется на анализ. 

**Логика метода brute force состоит из двух основных этапов:**  
* перебор всех возможных сдвигов алфавита (ключей) и расшифровка текста каждым из этих ключей  
* анализ результата расшифровки и принятие решения какой ключ является корректным

**Логика анализа результата:**
После того, как текст расшифрован подобранным ключом, генерируется словарь слов, в основе которого лежит расшифрованный текст. 
Также словарь генерируется по тексту, предоставленному на анализ.
Затем оба словаря сравниваются: считается количество слов, встречающихся в обоих словарях (пересечение множеств).  
Результат запоминается.  
Процесс повторяется на каждой итерации (после расшифровки текста каждым из подобранных ключей):  
`текст расшифровывается` -> `результат анализируется` -> `результат запоминается`  

Когда все ключи перебраны, ищется лучший результат. Лучший результат - это тот, где количество слов, совпадающих в двух словарях, максимально. 
Лучший результат достигается, когда в основе словаря лежит текст, расшифрованный верным ключом. 
Когда верный ключ найден, текст расшифровывается еще раз. Результат сохраняется в файл. 

В пакете `decrypt_statistics_analysis` содержатся:  
`DecryptStatisticsAnalysis` - класс, описывающий основную логику расшифровки текста методом статистического анализа текста
`CharacterStatistics` - вспомогательный класс, генерирующий статистику первых 1000 символов в тексте, предоставленному на вход. 
`PopularCharFinder` - вспомогательный класс, который определеяет самый часто встречающийся сивол на основе статистики. 

Класс `DecryptStatisticsAnalysis` работает с двумя текстами: текст, который нужно расшифровать и текст, который предоставляется на анализ. 

**Логика метода статистического анализа состоит из четырех основных этапов:**  
* Генерируется статистика символов по каждому из предоставленных на вход текстов: какой символ и сколько раз встретился. 
* Определяется самый часто встречающийся символ для каждого из текстов в отдельности.
* Предполагается, что символы, являющиеся самыми популярными в каждом их текстов - это один и тот же символ, просто в зашифрованном тексте этот символ зашифрован. 
Таким образом, расчитывается какой сдвиг нужно сделать, чтобы из популярного символа в зашифрованном тексте получить популярныый символ из текста на анализ.
* После того как верный ключ найден, осуществляется расшифровка текста. Результат сохраняется в файл. 

## Примеры

**Шифровка текста по заданному сдвигу:**

Пример запроса, когда файлы и .jar находятся в одной папке:

>java -Dfile.encoding=UTF-8 -jar ./my-main-v-0.0.1.jar encrypt -input=input1.txt -output=result1.txt -shift=20

Пример запроса, когда файлы и .jar находятся в разных папках:

Пример ответа:
>Encryption completed successfully.
Please check results here: C:\Users\Image1\Desktop\Text\chekhov\result1.txt

_Далее, для удобства чтеня будут приведены только примеры запроса, когда файлы и .jar находятся в одной папке._

>java -Dfile.encoding=UTF-8 -jar ./my-main-v-0.0.1.jar encrypt -input=C:\Users\Image1\Desktop\Text\chekhov\input1.txt -output=C:\Users\Image1\Desktop\Text\chekhov\result1.txt -shift=20

***

**Расшифровка текста по заданному сдвигу:**

Пример запроса:
>java -Dfile.encoding=UTF-8 -jar ./my-main-v-0.0.1.jar decryptWithShift -input=input.txt -output=result2.txt -shift=20

Пример ответа:
>Decryption completed successfully.
Please check results here: C:\Users\Image1\Desktop\Text\chekhov\result2.txt

***

**Расшифровка текста методом brute force:**

Пример запроса:
>java -Dfile.encoding=UTF-8 -jar ./my-main-v-0.0.1.jar decryptWithBruteForce -input=input.txt -output=result3.txt -textToAnalyse=textToAnalyse.txt

Пример ответа:
>Decryption by Brute Force method completed successfully.
Please check results here: C:\Users\Image1\Desktop\Text\chekhov\result3.txt

***

**Расшифровка текста методом статистического анализа текста:**

Пример запроса:
>java -Dfile.encoding=UTF-8 -jar ./my-main-v-0.0.1.jar decryptWithStatAnalysis -input=input.txt -output=result4.txt -textToAnalyse=textToAnalyse.txt

Пример ответа:
>Decryption by Statistics Analysis method completed successfully.
Please check results here: C:\Users\Image1\Desktop\Text\chekhov\result4.txt