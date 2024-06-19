/* Programa hecho por Salvador López Criado.
 *
 *
 *
 * Este programa conecta a una red Wi-Fi y crea un servidor web en el ESP32.
 * El servidor web tiene 3 botones para controlar el estado de los LEDs del ESP32.
 */

#include <stdio.h>
#include <string.h>
#include "freertos/FreeRTOS.h"
#include "freertos/task.h"
#include "esp_wifi.h"
#include "esp_event.h"
#include "esp_log.h"
#include "nvs_flash.h"
#include "lwip/err.h"
#include "lwip/sys.h"
#include "esp_http_server.h"

#define LED1_PIN 1
#define LED2_PIN 2
#define LED3_PIN 3

static const char *TAG = "web_server";


bool LED1status = true;
bool LED2status = false;
bool LED3status = true;

static esp_err_t root_get_handler(httpd_req_t *req);
static esp_err_t led_control_handler(httpd_req_t *req);

httpd_uri_t uri_get = {
    .uri = "/",
    .method = HTTP_GET,
    .handler = root_get_handler,
    .user_ctx = NULL
};

httpd_uri_t uri_led1on = {
    .uri = "/led1on",
    .method = HTTP_GET,
    .handler = led_control_handler,
    .user_ctx = (void*)LED1_PIN
};

httpd_uri_t uri_led1off = {
    .uri = "/led1off",
    .method = HTTP_GET,
    .handler = led_control_handler,
    .user_ctx = (void*)LED1_PIN
};

httpd_uri_t uri_led2on = {
    .uri = "/led2on",
    .method = HTTP_GET,
    .handler = led_control_handler,
    .user_ctx = (void*)LED2_PIN
};

httpd_uri_t uri_led2off = {
    .uri = "/led2off",
    .method = HTTP_GET,
    .handler = led_control_handler,
    .user_ctx = (void*)LED2_PIN
};

httpd_uri_t uri_led3on = {
    .uri = "/led3on",
    .method = HTTP_GET,
    .handler = led_control_handler,
    .user_ctx = (void*)LED3_PIN
};

httpd_uri_t uri_led3off = {
    .uri = "/led3off",
    .method = HTTP_GET,
    .handler = led_control_handler,
    .user_ctx = (void*)LED3_PIN
};

// HTML para el control de LEDs
const char *HTML_TEMPLATE =
    "<!DOCTYPE html>"
    "<html>"
    "<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">"
    "<title>LED Control</title>"
    "<style>html { font-family: Helvetica; display: inline-block; margin: 0px auto; text-align: center;}"
    "body{margin-top: 50px;} h1 {color: #444444;margin: 50px auto 30px;} h3 {color: #444444;margin-bottom: 50px;}"
    ".button {display: block;width: 80px;background-color: #3498db;border: none;color: white;padding: 13px 30px;text-decoration: none;font-size: 25px;margin: 0px auto 35px;cursor: pointer;border-radius: 4px;}"
    ".button-on {background-color: #3498db;}"
    ".button-on:active {background-color: #2980b9;}"
    ".button-off {background-color: #34495e;}"
    ".button-off:active {background-color: #2c3e50;}"
    "p {font-size: 14px;color: #888;margin-bottom: 10px;}"
    "</style>"
    "</head>"
    "<body>"
    "<h1>ESP32 Web Server</h1>"
    "<h3>Using Station(STA) Mode</h3>"
    "<p>LED1 Status: %s</p><a class=\"button %s\" href=\"/led1%s\">%s</a>"
    "<p>LED2 Status: %s</p><a class=\"button %s\" href=\"/led2%s\">%s</a>"
    "<p>LED3 Status: %s</p><a class=\"button %s\" href=\"/led3%s\">%s</a>"
    "</body>"
    "</html>";

static void init_wifi(void)
{
    wifi_init_config_t cfg = WIFI_INIT_CONFIG_DEFAULT();
    ESP_ERROR_CHECK(esp_wifi_init(&cfg));
    ESP_ERROR_CHECK(esp_wifi_set_mode(WIFI_MODE_STA));
    wifi_config_t wifi_config = {
        .sta = {
            .ssid = "Nombre",
            .password = "contraseña",
        },
    };
    ESP_ERROR_CHECK(esp_wifi_set_config(ESP_IF_WIFI_STA, &wifi_config));
    ESP_ERROR_CHECK(esp_wifi_start());
    ESP_ERROR_CHECK(esp_wifi_connect());
}

static esp_err_t root_get_handler(httpd_req_t *req)
{
    char led1_status[4], led2_status[4], led3_status[4];
    char led1_button[7], led2_button[7], led3_button[7];
    char led1_action[4], led2_action[4], led3_action[4];

    strcpy(led1_status, LED1status ? "OFF" : "ON");
    strcpy(led2_status, LED2status ? "ON" : "OFF");
    strcpy(led3_status, LED3status ? "OFF" : "ON");

    strcpy(led1_button, LED1status ? "button-off" : "button-on");
    strcpy(led2_button, LED2status ? "button-on" : "button-off");
    strcpy(led3_button, LED3status ? "button-off" : "button-on");

    strcpy(led1_action, LED1status ? "off" : "on");
    strcpy(led2_action, LED2status ? "off" : "on");
    strcpy(led3_action, LED3status ? "off" : "on");

    char response[1024];
    snprintf(response, sizeof(response), HTML_TEMPLATE,
             led1_status, led1_button, led1_action, led1_status,
             led2_status, led2_button, led2_action, led2_status,
             led3_status, led3_button, led3_action, led3_status);

    httpd_resp_send(req, response, strlen(response));
    return ESP_OK;
}

static esp_err_t led_control_handler(httpd_req_t *req)
{
    int pin = (int)req->user_ctx;

    if (pin == LED1_PIN)
        LED1status = !LED1status;
    else if (pin == LED2_PIN)
        LED2status = !LED2status;
    else if (pin == LED3_PIN)
        LED3status = !LED3status;

    httpd_resp_send(req, "LED toggled", HTTPD_RESP_USE_STRLEN);
    return ESP_OK;
}

static httpd_handle_t start_webserver(void)
{
    httpd_config_t config = HTTPD_DEFAULT_CONFIG();

    httpd_handle_t server = NULL;
    if (httpd_start(&server, &config) == ESP_OK) {
        httpd_register_uri_handler(server, &uri_get);
        httpd_register_uri_handler(server, &uri_led1on);
        httpd_register_uri_handler(server, &uri_led1off);
        httpd_register_uri_handler(server, &uri_led2on);
        httpd_register_uri_handler(server, &uri_led2off);
        httpd_register_uri_handler(server, &uri_led3on);
        httpd_register_uri_handler(server, &uri_led3off);
    }
    return server;
}

void app_main(void)
{
    esp_err_t ret = nvs_flash_init();
    if (ret == ESP_ERR_NVS_NO_FREE_PAGES || ret == ESP_ERR_NVS_NEW_VERSION_FOUND) {
      ESP_ERROR_CHECK(nvs_flash_erase());
      ret = nvs_flash_init();
    }
    ESP_ERROR_CHECK(ret);

    init_wifi();

    gpio_pad_select_gpio(LED1_PIN);
    gpio_set_direction(LED1_PIN, GPIO_MODE_OUTPUT);
    gpio_pad_select_gpio(LED2_PIN);
    gpio_set_direction(LED2_PIN, GPIO_MODE_OUTPUT);
    gpio_pad_select_gpio(LED3_PIN);
    gpio_set_direction(LED3_PIN, GPIO_MODE_OUTPUT);

    gpio_set_level(LED1_PIN, 1);
    gpio_set_level(LED2_PIN, 0);
    gpio_set_level(LED3_PIN, 1);

    httpd_handle_t server = start_webserver();
    if (server == NULL) {
        ESP_LOGI(TAG, "Error starting server!");
    } else {
        ESP_LOGI(TAG, "HTTP server started");
    }
}
