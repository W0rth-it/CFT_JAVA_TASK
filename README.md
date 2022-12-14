# CFT_JAVA_TASK
Запуск:
```\out\artifacts\JINT_jar java -jar .\JINT.jar -i out.txt in1.txt in2.txt```

Версии:
maven 3.8.1, 
jdk-17.0.2


Алгоритм (сортировка по возрастанию): Из списка изначальных входных файлов выбирается два файла. Считывается по одной строке их каждого файла, элементы сравниваются, меньший элемент записывается в новый временный файл. Далее сравнивается незаписанный элемент из прошлой стадии с новым считанным элементом из того файла, в котором находился записанный на прошлой стадии элемент, меньший из них записывается, алгоритм повторяется до окончания одного из файлов, затем остатки второго записываются подряд. Алгоритм проходит по всем n файлом, таким образом получается n/2 файлов, каждый состоящий из элементов исходных файлов. Алгоритм повторяется до тех пор пока не останется 1 файл, он и является нашим ответом. В случае нечетного количества файлов, последний файл остается на конец и сливается последним действием.

Если в файле есть элементы идущие не по порядку, например сортировка по возрастанию: **[1, 2, 3, -1, 0, 4]**, то элементы **[-1, 0]** будут пропущены так как при считывании файла алгоритм проверяет чтобы элемент был больше (сортировка по возрастанию) чем предыдущий, а если нет то он игнорируется.
