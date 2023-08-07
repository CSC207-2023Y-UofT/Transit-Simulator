package model.train.track;

import model.Direction;
import model.control.TransitModel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

import javax.sound.midi.Track;

public class TrackSegmentTest {
    public static TransitModel transitModel;
    public static TrackSegment trackSegment1;
    public static TrackSegment trackSegment2;

    @BeforeEach
    public void reset() {
        transitModel = new TransitModel();
        trackSegment1 = new TrackSegment(transitModel.getTrackRepo(), "trackSegment1", 100);
        trackSegment2 = new TrackSegment(transitModel.getTrackRepo(), "trackSegment2", 100);
    }

    @Test
    public void testGetRepo() {
        Assertions.assertSame(transitModel.getTrackRepo(), trackSegment1.getRepo());
    }

    @Test
    public void testGetLength() {
        Assertions.assertEquals(100, trackSegment1.getLength());
    }

    @Test
    public void testGetId() {
        Assertions.assertEquals("trackSegment1", trackSegment1.getId());
    }

    @Test
    public void testGetNode() {
        Assertions.assertFalse(trackSegment1.getNode().isPresent());
    }

    @Test
    public void testGetNext() {
        Assertions.assertNull(trackSegment1.getNext());
        trackSegment1.linkForward(trackSegment2);
        Assertions.assertSame(trackSegment2, trackSegment1.getNext());
    }

    @Test
    public void testGetPrev() {
        Assertions.assertNull(trackSegment1.getPrev());
        trackSegment1.linkBackward(trackSegment2);
        Assertions.assertSame(trackSegment2, trackSegment1.getPrev());
    }

    @Test
    public void testGetNextOneParam() {
        trackSegment1.linkForward(trackSegment2);
        Assertions.assertSame(trackSegment2, trackSegment1.getNext(Direction.FORWARD));
        Assertions.assertNull(trackSegment1.getNext(Direction.BACKWARD));
    }

    // .linkForward() and .linkBackward() are basically .link() with a direction parameter, it's the same

    @Test
    public void testLink() {
        trackSegment1.linkForward(trackSegment2);
        Assertions.assertSame(trackSegment2, trackSegment1.getNext());
        Assertions.assertSame(trackSegment1, trackSegment2.getPrev());
    }

    @Test
    public void testLinkAlreadyLinked() {
        trackSegment1.linkForward(trackSegment2);
        trackSegment1.linkForward(trackSegment2);
        Assertions.assertSame(trackSegment2, trackSegment1.getNext());
        Assertions.assertSame(trackSegment1, trackSegment2.getPrev());
    }

    @Test
    public void testLinkFirstPrecondition() {
        TrackSegment trackSegment3 = new TrackSegment(transitModel.getTrackRepo(), "trackSegment3", 100);
        trackSegment1.linkForward(trackSegment2);
        Assertions.assertThrows(IllegalStateException.class, () -> trackSegment1.linkForward(trackSegment3));
    }

    @Test
    public void testLinkSecondPrecondition() {
        TrackSegment trackSegment3 = new TrackSegment(transitModel.getTrackRepo(), "trackSegment3", 100);
        trackSegment2.linkForward(trackSegment3);
        Assertions.assertThrows(IllegalStateException.class, () -> trackSegment1.linkForward(trackSegment3));
    }

    @Test
    public void testUnlink() {
        trackSegment1.linkForward(trackSegment2);
        TrackSegment.unlink(trackSegment1, trackSegment2);
        Assertions.assertNull(trackSegment1.getNext());
        Assertions.assertNull(trackSegment2.getPrev());
    }

    @Test
    public void testUnlinkAlreadyUnlinked() {
        TrackSegment.unlink(trackSegment1, trackSegment2);
        Assertions.assertNull(trackSegment1.getNext());
        Assertions.assertNull(trackSegment2.getPrev());
    }

    @Test
    public void testUnlinkSecondPrecondition() {
        TrackSegment trackSegment3 = new TrackSegment(transitModel.getTrackRepo(), "trackSegment3", 100);
        trackSegment2.linkForward(trackSegment3);
        Assertions.assertThrows(IllegalStateException.class, () -> TrackSegment.unlink(trackSegment1, trackSegment3));
    }

    @DisplayName("TrackSegmentTest Class Teardown")
    @AfterAll
    public static void teardown() {
        transitModel = null;
        trackSegment1 = null;
    }
}
