package tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BoardPage;
import pages.CardPage;
import pages.ListPage;
import utils.BaseTest;

import java.util.List;

/**
 * Drag and Drop Test Suite
 * 
 * ⚠️ EXPERIMENTAL - Recently hardened but may still be flaky:
 * - Added scroll-into-view before all drag operations
 * - Increased pauses from 1000ms to 1500ms
 * - Added JavaScript fallback for card drags
 * - Added offset-based fallback for list drags
 * - Increased headless viewport to 1920x1080
 * 
 * Hardening applied:
 * ✅ Element re-location after scroll (fixes staleness)
 * ✅ Viewport constraint handling (lists >800px apart)
 * ✅ JS drag fallback when Actions API fails
 * ✅ Longer pauses for React re-renders
 * 
 * Known limitations:
 * - Still relies on Selenium Actions API (inherently fragile with SPAs)
 * - Trello's optimistic UI can cause race conditions
 * - Headless mode more flaky than headed
 * 
 * PREREQUISITE: Requires pre-configured board named "Trello Project" with:
 * - Lists: "To Do", "Doing", "Done"
 * - Cards in "To Do" list: "Create test cases", "implement test cases", "Prepare test script"
 * 
 * Set SKIP_ALL_DRAG_TESTS = true to disable if flakiness returns.
 */
public class DragAndDrop_Test extends BaseTest {

    private static final String BOARD_NAME = "Trello Project";
    private static final boolean SKIP_ALL_DRAG_TESTS = false;  // Set to true if flakiness returns

    /**
     * Runs once before all tests in this class.
     * Opens one browser, logs in, and navigates to the board.
     * Replaces BaseTest @BeforeMethod / @AfterMethod so that the browser
     * is NOT recreated between tests.
     */
    @BeforeClass
    public void setUp() {
        super.setUp();                   // initialises config, driver, loginPage, dashboardPage
        
        if (SKIP_ALL_DRAG_TESTS) {
            throw new SkipException(
                "Drag-and-drop tests are disabled due to high flakiness. " +
                "Set SKIP_ALL_DRAG_TESTS = false in DragAndDrop_Test.java to attempt running them. " +
                "Known issues: Dynamic DOM updates, headless viewport constraints, timing race conditions."
            );
        }
        
        performLogin();
        dismissCookieBannerIfPresent();
        
        // Check if board exists - skip entire test class if not
        if (!dashboardPage.isBoardPresent(BOARD_NAME)) {
            throw new SkipException(
                "PREREQUISITE MISSING: Board '" + BOARD_NAME + "' not found. " +
                "This test requires a pre-configured board with lists (To Do, Doing, Done) " +
                "and test cards. Please create the board manually or run the setup script."
            );
        }
        
        dashboardPage.openBoard(BOARD_NAME);
        
        // Wait for board to fully load
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        
        // Verify required lists exist
        verifyRequiredListsExist();
        
        // Verify required cards exist
        verifyRequiredCardsExist();
    }
    
    /**
     * Verifies the three required lists exist on the board.
     * Skips test if any are missing.
     */
    private void verifyRequiredListsExist() {
        ListPage listPage = new ListPage(driver);
        String[] requiredLists = {"To Do", "Doing", "Done"};
        
        for (String listName : requiredLists) {
            if (!listPage.isListCreated(listName)) {
                throw new SkipException(
                    "PREREQUISITE MISSING: List '" + listName + "' not found on board '" + BOARD_NAME + "'. " +
                    "Please create the required lists: To Do, Doing, Done"
                );
            }
        }
    }
    
    /**
     * Verifies the required test cards exist in the To Do list.
     * Skips test if any are missing.
     */
    private void verifyRequiredCardsExist() {
        CardPage cardPage = new CardPage(driver);
        String[] requiredCards = {
            "Create test cases",
            "implement test cases",
            "Prepare test script"
        };
        
        for (String cardName : requiredCards) {
            if (!cardPage.isCardCreated(cardName)) {
                throw new SkipException(
                    "PREREQUISITE MISSING: Card '" + cardName + "' not found in 'To Do' list on board '" + BOARD_NAME + "'. " +
                    "Please create the required cards: Create test cases, implement test cases, Prepare test script"
                );
            }
        }
    }

    /** Runs once after all tests in this class — quits the shared browser. */
    @AfterClass
    public void tearDown() {
        super.tearDown();
    }

    // ── TC-1: verify the project board opens correctly ───────────────────────
    @Test(priority = 1)
    public void openBoard() {
        BoardPage boardPage = new BoardPage(driver);
        Assert.assertEquals(boardPage.getBoardTitle(), BOARD_NAME,
                "Board title should match after opening");
    }

    // ── TC-2: drag a card from one list to another ───────────────────────────
    @Test(priority = 2)
    public void DragAndDrop_CardFromOneListToOther() {
        String cardName   = "Create test cases";
        String sourceList = "To Do";
        String targetList = "Doing";

        CardPage cardPage = new CardPage(driver);
        cardPage.dragCardToList(cardName, sourceList, targetList);

        Assert.assertEquals(
                cardPage.countCardsInList(cardName, sourceList), 0,
                "Card '" + cardName + "' should no longer appear in '" + sourceList + "'"
        );
        Assert.assertTrue(
                cardPage.isCardInList(cardName, targetList),
                "Card '" + cardName + "' should now appear in '" + targetList + "'"
        );
    }

    // ── TC-3: drag a card within the same list to reorder it ─────────────────
    @Test(priority = 3, enabled = true)
    public void DragAndDrop_CardInAList() {
        String sourceCard = "implement test cases";
        String targetCard = "Prepare test script";
        String listName   = "To Do";

        CardPage cardPage = new CardPage(driver);
        int indexBefore = cardPage.getCardIndexInList(sourceCard, listName);

        cardPage.dragCardInList(sourceCard, targetCard, listName);

        int indexAfter = cardPage.getCardIndexInList(sourceCard, listName);
        Assert.assertNotEquals(indexAfter, indexBefore,
                "Card '" + sourceCard + "' should have changed position within the list");
    }

    // ── TC-4: drag an entire list to reorder it on the board ─────────────────
    @Test(priority = 4, enabled = true)
    public void DragAndDrop_EntireList() {
        String sourceList = "Doing";
        String destList   = "Done";

        BoardPage boardPage = new BoardPage(driver);
        List<String> namesBefore = boardPage.getListOrder();
        int indexBefore = namesBefore.indexOf(sourceList);

        boardPage.dragListToPosition(sourceList, destList);
        boardPage.waitForListReorder(sourceList, indexBefore);

        List<String> namesAfter = boardPage.getListOrder();
        int indexAfter = namesAfter.indexOf(sourceList);

        Assert.assertTrue(namesAfter.contains(sourceList),
                "List '" + sourceList + "' should still exist after reordering");
        Assert.assertTrue(namesAfter.contains(destList),
                "List '" + destList + "' should still exist after reordering");
        Assert.assertNotEquals(indexAfter, indexBefore,
                "List '" + sourceList + "' should have changed position on the board");
    }

    // ── TC-5: drag card to invalid zone — should return to original position ──
    @Test(priority = 5, enabled = true)
    public void DragCard_OutsideScope_ReturnsToPosition() {
        String cardText = "Prepare test script";

        CardPage cardPage = new CardPage(driver);

        // Find which list the card is currently in (does not assume a fixed list)
        String listName = cardPage.findListContainingCard(cardText);
        Assert.assertNotNull(listName,
                "Card '" + cardText + "' was not found on the board before drag");

        int indexBefore = cardPage.getCardIndexInList(cardText, listName);

        cardPage.dragCardToInvalidTarget(cardText, listName);

        waitUntil(d -> cardPage.getCardIndexInList(cardText, listName) >= 0);

        int indexAfter = cardPage.getCardIndexInList(cardText, listName);
        Assert.assertTrue(indexAfter >= 0, "Card should still be present in the original list");
        Assert.assertEquals(indexAfter, indexBefore,
                "Card should return to its original position after invalid drag");
    }
}





