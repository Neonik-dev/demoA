Тестовое задание: https://docs.yandex.ru/docs/view?url=ya-disk-public%3A%2F%2FTYVc2nq7pvfDYgeWNuZtyiaXdX51FqT%2Flv9jX2SyyamXE5e%2BPxod65Xv3z7YQrt2MrZMrpRHvcl9PkWNPWglZQ%3D%3D&name=Тестовое%20middle%20Java.docx

Для запуска приложения необходимо скачать проект, зайти через командную строку в папку с проетком и выполнить следующие команды:

```docker build --tag demoA .```

```docker run --detach demoA```

После этого проект запустится в докере и будет доступен для локального использования.

Можно зайти по ссылке http://localhost:8080/swagger-ui/index.html и будет доступен ```swagger```. В целом, там ничего интересного, все по тз.

Немного опишу происходящее в коде. Есть один контроллер, который принимает на вход ```msgA```. Затем вызывается класс ```AdapterFromAToB```. 
В нем происходит валидация, вызов необходимых сторонных сайтов погоды, формирование ```msgB``` и последующая отправка в сервис ```B```.

Я сделал 2 клиента для работы с сервисами погоды: ```OpenWeather``` и ```Gismeteo```. Для работы с этими сервисами необходимо получить токены, но так как токенов у меня не было, написал без них, не тестируя работоспособность.
К сожалению, не успел написать интеграционные тесты.

Все клиенты погоды расширяют интерфейс ```IOuterSiteClient```, который имеет метод ```Mono<Map<String, String>> getInfoByCoordinates```. Таким образом, в коде идет ассинхронная отправка запросов на сайты с погодой.
После того, как отправили все запросы, мы ждем ответа, чтобы сформировать ```msgB```.

Из интересного:
- Легко внедрить новые сайты для получения погодной информации
- Ассинхронная отправка запросов на сайты
- Написал интеграционные тесты для проверки валидации

Готов объяснить более подробно свое решение на встрече)
