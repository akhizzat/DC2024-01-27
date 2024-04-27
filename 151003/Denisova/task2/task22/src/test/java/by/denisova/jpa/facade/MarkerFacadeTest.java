package by.denisova.jpa.facade;

import by.denisova.jpa.dto.request.CreateMarkerDto;
import by.denisova.jpa.dto.request.UpdateMarkerDto;
import by.denisova.jpa.dto.response.MarkerResponseDto;
import by.denisova.jpa.mapper.MarkerMapper;
import by.denisova.jpa.model.Marker;
import by.denisova.jpa.service.MarkerService;
import by.denisova.jpa.util.dto.marker.request.CreateMarkerDtoTestBuilder;
import by.denisova.jpa.util.dto.marker.request.UpdateMarkerDtoTestBuilder;
import by.denisova.jpa.util.dto.marker.response.MarkerResponseDtoTestBuilder;
import by.denisova.jpa.util.entity.DetachedMarkerTestBuilder;
import by.denisova.jpa.util.entity.MarkerTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MarkerFacadeTest {

    @InjectMocks
    private MarkerFacade markerFacade;

    @Mock
    private MarkerService markerService;

    @Mock
    private MarkerMapper markerMapper;

    @Test
    @DisplayName("Test should return expected response")
    void findById() {
        final Marker persistedMarker = MarkerTestBuilder.marker().build();
        final MarkerResponseDto expected = MarkerResponseDtoTestBuilder.marker().build();

        doReturn(persistedMarker)
                .when(markerService)
                .findById(persistedMarker.getId());

        doReturn(expected)
                .when(markerMapper)
                .toMarkerResponse(persistedMarker);

        final MarkerResponseDto actual = markerFacade.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void findAll() {
        final Marker persistedMarker = MarkerTestBuilder.marker().build();
        final MarkerResponseDto expectedMarker = MarkerResponseDtoTestBuilder.marker().build();
        final List<MarkerResponseDto> expected = List.of(expectedMarker);

        doReturn(List.of(persistedMarker))
                .when(markerService)
                .findAll();

        doReturn(expectedMarker)
                .when(markerMapper)
                .toMarkerResponse(persistedMarker);

        final List<MarkerResponseDto> actual = markerFacade.findAll();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("Test should return expected response")
    void save() {
        final Marker detachedMarker = DetachedMarkerTestBuilder.marker().build();
        final CreateMarkerDto request = CreateMarkerDtoTestBuilder.marker().build();
        final Marker persistedMarker = MarkerTestBuilder.marker().build();
        final MarkerResponseDto expected = MarkerResponseDtoTestBuilder.marker().build();

        doReturn(detachedMarker)
                .when(markerMapper)
                .toMarker(request);

        doReturn(persistedMarker)
                .when(markerService)
                .save(detachedMarker);

        doReturn(expected)
                .when(markerMapper)
                .toMarkerResponse(persistedMarker);

        final MarkerResponseDto actual = markerFacade.save(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void update() {
        final UpdateMarkerDto request = UpdateMarkerDtoTestBuilder.marker().build();
        final Marker persistedMarker = MarkerTestBuilder.marker().build();
        final MarkerResponseDto expected = MarkerResponseDtoTestBuilder.marker().build();

        doReturn(persistedMarker)
                .when(markerService)
                .findById(persistedMarker.getId());

        doReturn(persistedMarker)
                .when(markerMapper)
                .toMarker(request, persistedMarker);

        doReturn(persistedMarker)
                .when(markerService)
                .update(persistedMarker);

        doReturn(expected)
                .when(markerMapper)
                .toMarkerResponse(persistedMarker);

        final MarkerResponseDto actual = markerFacade.update(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should complete successfully for provided id")
    void delete() {
        final Long markerId = 1L;

        doNothing().when(markerService)
                .deleteById(markerId);

        markerFacade.delete(markerId);

        verify(markerService, times(1)).deleteById(markerId);
    }
}
