package com.airwallex.codechallenge;

import com.airwallex.codechallenge.service.AlertingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppTest {

    @Mock
    private AlertingService alertingService;
    private App app = new App();

    @BeforeEach
    private void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field alertingServiceField = App.class.getDeclaredField("alertingService");
        alertingServiceField.setAccessible(true);
        alertingServiceField.set(app, alertingService);

        when(alertingService.produceAlert(any(List.class))).thenReturn(Collections.EMPTY_LIST);
    }

    @Test
    public void shouldInvokeAlertingServiceOnce() {
        app.main(new String[]{"example/input1.jsonl"});

        verify(alertingService, times(1)).produceAlert(any(List.class));
    }

    @Test
    public void shouldInvokeAlertingServiceTwice() {
        app.main(new String[]{"example/input2.jsonl"});

        verify(alertingService, times(2)).produceAlert(any(List.class));
    }
}
