package entity.model.train;

import entity.model.train.repo.impl.MemoryTrackRepo;
import entity.model.train.repo.TrackRepo;
import entity.model.train.track.TrackSegment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

public class TrackRepoTest {
    public static TrackRepo basicTrackRepo1;
    public static TrackRepo basicTrackRepo2;
    public static TrackSegment trackSegment1;
    public static TrackSegment trackSegment2;
    @DisplayName("TrackRepoTest Class Setup")
    @BeforeAll
    public static void setup() {
        basicTrackRepo1 = new MemoryTrackRepo();
        basicTrackRepo2 = new MemoryTrackRepo();
        trackSegment1 = new TrackSegment(basicTrackRepo1, "trackSegment1", 100);
        trackSegment2 = new TrackSegment(basicTrackRepo2, "trackSegment2", 100);

        basicTrackRepo1.addTrack(trackSegment1);
    }

    @Test
    public void testGetTracks() {
        TrackRepo trackRepo = new MemoryTrackRepo();
        Assertions.assertEquals(0, trackRepo.getTracks().size());
    }

    @Test
    public void testGetTrack() {
        Assertions.assertEquals(trackSegment1, basicTrackRepo1.getTrack("trackSegment1").orElse(null));
        Assertions.assertNull(basicTrackRepo1.getTrack("trackSegment2").orElse(null));
    }

    @Test
    public void testAddTrackFirstPrecondition() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicTrackRepo1.addTrack(trackSegment1));
    }

    @Test
    public void testAddTrackSecondPrecondition() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicTrackRepo1.addTrack(trackSegment2));
    }

    @Test
    public void testAddTrack() {
        TrackSegment trackSegment3 = new TrackSegment(basicTrackRepo1, "trackSegment3", 100);
        int size = basicTrackRepo1.getTracks().size();
        basicTrackRepo1.addTrack(trackSegment3);
        int newSize = basicTrackRepo1.getTracks().size();
        Assertions.assertEquals(size + 1, newSize);
        Assertions.assertEquals(trackSegment3, basicTrackRepo1.getTrack("trackSegment3").orElse(null));
    }

    @DisplayName("TrackRepoTest Class Teardown")
    @AfterAll
    public static void teardown() {
        basicTrackRepo1 = null;
        basicTrackRepo2 = null;
        trackSegment1 = null;
        trackSegment2 = null;
    }
}
