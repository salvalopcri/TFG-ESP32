#include "driver/touch_pad.h"
#include "driver/ledc.h"
#include "freertos/FreeRTOS.h"
#include "freertos/task.h"


#define LEDC_HS_TIMER          LEDC_TIMER_0
#define LEDC_HS_MODE           LEDC_HIGH_SPEED_MODE
#define LEDC_HS_CH0_CHANNEL    LEDC_CHANNEL_0
#define LEDC_HS_CH1_CHANNEL    LEDC_CHANNEL_1
#define LEDC_TEST_DUTY         (255)

#define TOUCH_PAD_LEFT_PIN     TOUCH_PAD_NUM6
#define TOUCH_PAD_RIGHT_PIN    TOUCH_PAD_NUM0

int brillo = 0;
int prueba = 5;
bool pinIzq = false, pinDer = false;

void init_touch_pad();
void init_ledc();
void comprobarPines();
void encenderLuces();

void app_main() {
    init_touch_pad();
    init_ledc();

    while (1) {
        comprobarPines();
        encenderLuces();
        vTaskDelay(10 / portTICK_PERIOD_MS);
    }
}

void init_touch_pad() {
    touch_pad_init();

    //Configuración de los  los pines táctiles. (Esto varía en función del modelo del ESP32).
    //Estos sirven para el ESP32 Wroover si no me equivoco.
    touch_pad_config(TOUCH_PAD_LEFT_PIN, 0);
    touch_pad_config(TOUCH_PAD_RIGHT_PIN, 0);
}

void init_ledc() {
    //Configuración del canal LEDC para el pin 2 (LED)
    ledc_timer_config_t ledc_timer = {
        .duty_resolution = LEDC_TIMER_8_BIT,
        .freq_hz = 5000,
        .speed_mode = LEDC_HS_MODE,
        .timer_num = LEDC_HS_TIMER
    };
    ledc_timer_config(&ledc_timer);

    ledc_channel_config_t ledc_channel = {
        .channel = LEDC_HS_CH0_CHANNEL,
        .duty = 0,
        .gpio_num = 2,
        .speed_mode = LEDC_HS_MODE,
        .timer_sel = LEDC_HS_TIMER
    };
    ledc_channel_config(&ledc_channel);
}



void comprobarPines() {
    uint16_t toqueIzq = 0, toqueDer = 0;

    //Leer los valores de los pines táctiles. Esto es corriente eléctrica. El valor nulo es
    //150 para el izquierdo y 120 para el derecho
    touch_pad_read(TOUCH_PAD_LEFT_PIN, &toqueIzq);
    touch_pad_read(TOUCH_PAD_RIGHT_PIN, &toqueDer);

    //Si baja de esos valores, significa que se están tocando.
    if (toqueIzq < 120 && toqueDer > 100) {
        pinIzq = true;
        pinDer = false;
    } else if (toqueDer < 100 && toqueIzq > 120) {
        pinDer = true;
        pinIzq = false;
    } else {
        pinIzq = false;
        pinDer = false;
    }
}

void encenderLuces() {
    if (pinIzq) {
        ledc_set_duty(LEDC_HS_MODE, LEDC_HS_CH0_CHANNEL, brillo);
        ledc_update_duty(LEDC_HS_MODE, LEDC_HS_CH0_CHANNEL);
        brillo += prueba;
        if (brillo <= 0 || brillo >= 255) {
            prueba = -prueba;
            vTaskDelay(10 / portTICK_PERIOD_MS); // Cambiar este retraso para la velocidad del fade
        }
    } else if (pinDer) {
        ledc_set_duty(LEDC_HS_MODE, LEDC_HS_CH0_CHANNEL, LEDC_TEST_DUTY);
        ledc_update_duty(LEDC_HS_MODE, LEDC_HS_CH0_CHANNEL);
    } else {
        ledc_set_duty(LEDC_HS_MODE, LEDC_HS_CH0_CHANNEL, 0);
        ledc_update_duty(LEDC_HS_MODE, LEDC_HS_CH0_CHANNEL);
    }
}
