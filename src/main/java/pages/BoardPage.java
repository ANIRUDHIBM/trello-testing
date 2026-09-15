package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BoardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ─────────────────────────────────────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────────────────────────────────────

    public BoardPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
    }


    // ─────────────────────────────────────────────────────────────────────────
    // LOCATORS — Board Creation
    // ─────────────────────────────────────────────────────────────────────────

    private final By headerCreateMenuBtn =
            By.cssSelector("button[data-testid='header-create-menu-button']");

    private final By headerCreateBoardBtn =
            By.cssSelector("button[data-testid='create-board-button']");

    private final By boardTitleInput =
            By.cssSelector(
                    "[data-testid='create-board-title-input']," +
                            "[placeholder='Add board title']," +
                            "[placeholder*='board title']," +
                            "[placeholder*='title']," +
                            "[placeholder*='Title']"
            );

    private final By finalCreateBtn =
            By.cssSelector("button[data-testid='create-board-submit-button']");


    // ─────────────────────────────────────────────────────────────────────────
    // LOCATORS — Board Header / Title
    // ─────────────────────────────────────────────────────────────────────────

    private final By boardTitleDisplay =
            By.cssSelector("h1[data-testid='board-name-display']");

    private final By boardTitleInputField =
            By.cssSelector("input[data-testid='board-name-input']");

    private final By boardStarBtn =
            By.cssSelector("button[aria-label='Star or unstar board']");


    // ─────────────────────────────────────────────────────────────────────────
    // LOCATORS — Board Menu (Show Menu / Background / Visibility)
    // ─────────────────────────────────────────────────────────────────────────

    private final By showMenuBtn =
            By.cssSelector(
                    "button[aria-label='Show menu']," +
                            "button[data-testid='overflow-menu-button']," +
                            "button[class*='board-header-btn-menu']"
            );

    private final By changeBackgroundBtn =
            By.xpath(
                    "//*[contains(@data-testid,'change-background')" +
                            " or contains(text(),'Change background')" +
                            " or contains(@class,'change-background')]"
            );

    private final By backgroundColorsOption =
            By.xpath(
                    "//*[contains(@data-testid,'background-colors')" +
                            " or contains(text(),'Colors')" +
                            " or contains(@class,'colors')]"
            );

    private final By colorTile =
            By.cssSelector(
                    "[class*='board-menu'] button[style*='background']," +
                            "[class*='popover'] button[style*='background']," +
                            "button[style*='background']," +
                            "[class*='background-box']"
            );

    private final By boardVisibilityBtn =
            By.cssSelector(
                    "button[data-testid*='visibility']," +
                            "button[aria-label*='Visibility']," +
                            "button[id*='permission']," +
                            "button[class*='vis']"
            );

    private final By privateVisibilityOption =
            By.xpath(
                    "//*[contains(@data-testid,'private')" +
                            " or contains(text(),'Private')" +
                            " or contains(@class,'private')]"
            );


    // ─────────────────────────────────────────────────────────────────────────
    // LOCATORS — Close / Reopen / Permanently Delete Board
    // ─────────────────────────────────────────────────────────────────────────

    private final By closeBoardMenuLink =
            By.xpath(
                    "//*[contains(@data-testid,'close-board')" +
                            " or contains(text(),'Close board')" +
                            " or contains(@class,'js-close-board')]"
            );

    private final By closeConfirmBtn =
            By.xpath(
                    "//button[normalize-space(.)='Close']" +
                            " | //input[@value='Close']" +
                            " | //*[contains(@data-testid,'confirm-button')]" +
                            " | //*[contains(@class,'js-confirm')]"
            );

    private final By closedBoardMessage =
            By.xpath(
                    "//*[contains(text(),'This board is closed')" +
                            " or contains(text(),'board is closed')" +
                            " or @data-testid='close-board-big-message'" +
                            " or @data-testid='close-board-message']"
            );

    private final By reopenBoardBtn =
            By.xpath(
                    "//button[@data-testid='workspace-chooser-trigger-button'" +
                            " and contains(.,'Reopen')]" +
                            " | //button[@data-testid='workspace-chooser-reopen-button']" +
                            " | //button[normalize-space(.)='Reopen board']"
            );

    private final By permanentDeleteLink =
            By.xpath(
                    "//*[contains(@data-testid,'delete-board')" +
                            " or contains(text(),'Permanently delete board')" +
                            " or contains(@class,'js-delete-board')]"
            );

    /** Confirm button for PERMANENTLY DELETING a board. */
    private final By boardDeleteConfirmBtn =
            By.xpath(
                    "//button[normalize-space(.)='Delete']" +
                            " | //input[@value='Delete']" +
                            " | //*[contains(@data-testid,'confirm-button')]"
            );


    // ─────────────────────────────────────────────────────────────────────────
    // LOCATORS — Board Menu (Archive flow)
    // ─────────────────────────────────────────────────────────────────────────

    private final By boardMenuButton = By.cssSelector("button[aria-label='Show menu']");
    private final By closePanelButton  = By.cssSelector("button[aria-label='Close popover']");
    private final By archivedItemPanel = By.cssSelector("section.BlZsLdklhFlfey");
    private final By archivedItemsOption = By.xpath("//*[normalize-space()='Archived items']");
    private final By archivedItemsPanel = By.cssSelector("[data-testid='board-menu-container']");

    // ─────────────────────────────────────────────────────────────────────────
    // LOCATORS — Archived Items Panel: Delete Flow
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Locates the archived item row/container that holds the given card title.
     * NOTE: Inspect the archived items panel in DevTools and update this
     *       XPath if the structure is different in your Trello version.
     */
    private By archivedCardItemLocator(String cardTitle) {
        return By.xpath(
                "//div[@data-testid='archived-card']" +
                        "[.//a[@data-testid='card-name' and normalize-space()='" + cardTitle + "']]"
        );
    }

    /**
     * Locates the Delete button INSIDE a specific archived card row.
     * Falls back to a text-based match if data-testid is not present.
     */
    private By deleteButtonInsideArchivedCard(String cardTitle) {
            return By.xpath(
                    "//button[@aria-label='Delete " + cardTitle + "']"
            );
    }

    /**
     * Confirm button for DELETING A CARD from the Archived Items panel.
     * Trello shows a popup — this targets the confirm button inside that popup.
     */
    private final By archivedCardDeleteConfirmBtn (String cardTitle) {
        return By.xpath(
                "//button[@aria-label='Permanently delete " + cardTitle + "']"
        );
    }

    /**
     * Verifies the archived card is GONE from the archived items panel after deletion.
     */
    private By archivedCardByTitle(String cardTitle) {
        return By.xpath(
                "//*[@data-testid='archived-card-list-item']" +
                        "//*[normalize-space(text())='" + cardTitle + "']"
        );
    }


    // ─────────────────────────────────────────────────────────────────────────
    // HELPERS — Resilient Click / Type
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Resilient click helper that falls back to Actions then JavascriptExecutor
     * if a standard click is intercepted (e.g. by a React overlay).
     *
     * @param locator The By locator of the element to click.
     */
    private void safeClick(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
        try {
            element.click();
        } catch (Exception e) {
            try {
                new org.openqa.selenium.interactions.Actions(driver)
                        .moveToElement(element).click().perform();
            } catch (Exception ex) {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", element);
            }
        }
    }

    /**
     * Resilient sendKeys helper that waits for visibility and clears before typing.
     *
     * @param locator The By locator of the input element.
     * @param text    The text to type.
     */
    private void safeType(By locator, String text) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        try {
            element.clear();
        } catch (Exception e) {
            // Some input elements do not support clear()
        }
        element.sendKeys(text);
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Board Navigation
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Finds and clicks an existing board tile by its visible name.
     *
     * @param boardName The exact name of the board as displayed on the home screen.
     */
    public void openExistingBoard(String boardName) {
        WebElement board = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//a[@title='" + boardName + "'" +
                                        " and @aria-label='" + boardName + "']"
                        )
                )
        );
        board.click();
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Board Creation
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Creates a new Trello board with the specified name.
     * Retries opening the create dropdown up to 3 times to survive SPA timing.
     *
     * @param name The name to give the new board.
     */
    public void createNewBoard(String name) {
        // Brief pause to let SPAs fully initialize event handlers on EAGER load
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        // Click create button and retry if the menu doesn't appear
        int retries    = 3;
        boolean menuOpened = false;

        while (retries > 0 && !menuOpened) {
            safeClick(headerCreateMenuBtn);
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
                shortWait.until(
                        ExpectedConditions.visibilityOfElementLocated(headerCreateBoardBtn)
                );
                menuOpened = true;
            } catch (Exception e) {
                System.out.println(
                        "Dropdown menu didn't open. Retrying... (Retries left: " + (retries - 1) + ")"
                );
                retries--;
            }
        }

        safeClick(headerCreateBoardBtn);

        try {
            safeType(boardTitleInput, name);
        } catch (Exception e) {
            System.out.println("DEBUG: Diagnostics dump for all matching elements in DOM:");
            try {
                for (WebElement el : driver.findElements(
                        By.cssSelector("input, textarea, button, [data-testid]"))
                ) {
                    String tag         = el.getTagName();
                    String id          = el.getAttribute("id");
                    String placeholder = el.getAttribute("placeholder");
                    String testid      = el.getAttribute("data-testid");
                    String nameAttr    = el.getAttribute("name");

                    if ((testid != null && !testid.isEmpty())
                            || (placeholder != null && !placeholder.isEmpty())
                            || "input".equals(tag)
                            || "textarea".equals(tag)) {
                        System.out.println(
                                " -> TAG: " + tag +
                                        " | ID: " + id +
                                        " | TESTID: " + testid +
                                        " | NAME: " + nameAttr +
                                        " | PLACEHOLDER: " + placeholder
                        );
                    }
                }
            } catch (Exception ex) {
                System.out.println("Diagnostics dump failed: " + ex.getMessage());
            }
            throw e;
        }

        safeClick(finalCreateBtn);

        // Wait until redirected to the new board URL
        wait.until(ExpectedConditions.urlContains("/b/"));
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Board Title / Star
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the currently displayed board title text.
     *
     * @return The board title as a trimmed string.
     */
    public String getBoardTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(boardTitleDisplay)
        ).getText().trim();
    }

    /**
     * Renames the board to the given new name.
     *
     * @param newName The new board title to set.
     */
    public void updateBoardTitle(String newName) {
        safeClick(boardTitleDisplay);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        WebElement titleInput = wait.until(
                ExpectedConditions.elementToBeClickable(boardTitleInputField)
        );
        titleInput.click();
        titleInput.sendKeys(Keys.CONTROL + "a");
        titleInput.sendKeys(Keys.BACK_SPACE);
        titleInput.sendKeys(newName);
        titleInput.sendKeys(Keys.ENTER);

        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(boardTitleDisplay, newName)
        );

        // Click the board canvas to release focus from the edit field
        try {
            driver.findElement(
                    By.cssSelector("div.board-canvas, #board, .board-main-content")
            ).click();
        } catch (Exception ignored) {}

        // Let the React UI fully settle
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
    }

    /**
     * Stars or unstars the board by clicking the star button.
     */
    public void toggleStarBoard() {
        safeClick(boardStarBtn);
    }

    /**
     * Returns true if the board is currently starred.
     * "Unstar board" in aria-label means it IS starred; "Star board" means it is not.
     *
     * @return true if starred, false otherwise.
     */
    public boolean isBoardStarred() {
        WebElement starButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(boardStarBtn)
        );
        String label = starButton.getAttribute("aria-label");
        return label != null && label.contains("Unstar");
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Board Background / Visibility
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Changes the board background to a solid color via the board menu.
     */
    public void changeBackgroundToColor() {
        try {
            safeClick(showMenuBtn);
        } catch (Exception e) {
            // Menu might already be open or hidden
        }
        safeClick(changeBackgroundBtn);
        safeClick(backgroundColorsOption);
        safeClick(colorTile);
    }

    /**
     * Changes the board visibility to Private.
     */
    public void changeVisibilityToPrivate() {
        safeClick(boardVisibilityBtn);
        safeClick(privateVisibilityOption);
    }

    /**
     * Returns the current visibility label text of the board.
     *
     * @return The aria-label or text of the visibility button.
     */
    public String getVisibilityText() {
        WebElement btn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(boardVisibilityBtn)
        );
        String ariaLabel = btn.getAttribute("aria-label");
        if (ariaLabel != null && !ariaLabel.isEmpty()) {
            return ariaLabel;
        }
        return btn.getText().trim();
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Close / Reopen / Permanently Delete Board
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Closes (archives) the board via the board menu.
     */
    public void closeBoard() {
        System.out.println("[DEBUG closeBoard] Starting closeBoard procedure...");
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));

        // Step 1: Check if already closed
        try {
            if (driver.findElement(closedBoardMessage).isDisplayed()) {
                System.out.println("[DEBUG closeBoard] Board is already closed!");
                return;
            }
        } catch (Exception ignored) {}

        // Step 2: Open menu if not already open
        try {
            System.out.println("[DEBUG closeBoard] Checking if menu is already open...");
            driver.findElement(closeBoardMenuLink);
            System.out.println("[DEBUG closeBoard] Menu is already open.");
        } catch (Exception e) {
            System.out.println("[DEBUG closeBoard] Menu not open. Clicking showMenuBtn...");
            try {
                WebElement menuBtn = wait.until(
                        ExpectedConditions.elementToBeClickable(showMenuBtn)
                );
                System.out.println(
                        "[DEBUG closeBoard] showMenuBtn found — text: '" +
                                menuBtn.getText() +
                                "', aria-label: '" +
                                menuBtn.getAttribute("aria-label") + "'"
                );
                safeClick(showMenuBtn);
                System.out.println("[DEBUG closeBoard] showMenuBtn clicked.");
            } catch (Exception ex) {
                System.out.println("[DEBUG closeBoard] showMenuBtn click failed: " + ex.getMessage());
            }
        }

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        // Step 3: Click 'More' sub-menu if Close Board is not directly visible
        boolean closeLinkVisible = false;
        try {
            closeLinkVisible = driver.findElement(closeBoardMenuLink).isDisplayed();
        } catch (Exception ignored) {}

        if (!closeLinkVisible) {
            System.out.println("[DEBUG closeBoard] Close Board link not visible — trying moreBtn...");
            try {
                By moreBtn = By.cssSelector(
                        "a.js-open-more," +
                                "button[class*='open-more']," +
                                "[data-testid='more-menu-button']," +
                                "li.js-open-more button"
                );
                WebElement mb = shortWait.until(
                        ExpectedConditions.elementToBeClickable(moreBtn)
                );
                System.out.println("[DEBUG closeBoard] moreBtn found. Clicking...");
                safeClick(moreBtn);
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            } catch (Exception e) {
                System.out.println("[DEBUG closeBoard] moreBtn not found: " + e.getMessage());
            }
        }

        // Step 4: Click Close Board
        try {
            System.out.println("[DEBUG closeBoard] Clicking closeBoardMenuLink...");
            WebElement closeLink = wait.until(
                    ExpectedConditions.elementToBeClickable(closeBoardMenuLink)
            );
            System.out.println(
                    "[DEBUG closeBoard] closeBoardMenuLink found — text: '" +
                            closeLink.getText() + "'"
            );
            safeClick(closeBoardMenuLink);
            System.out.println("[DEBUG closeBoard] closeBoardMenuLink clicked.");
        } catch (Exception e) {
            System.out.println("[DEBUG closeBoard] closeBoardMenuLink click failed: " + e.getMessage());
            System.out.println("[DEBUG closeBoard] Printing visible menu elements:");
            try {
                for (WebElement el : driver.findElements(By.cssSelector("a, button, li"))) {
                    String txt = el.getText().trim();
                    if (!txt.isEmpty() && (
                            txt.contains("Close") ||
                                    txt.contains("Delete") ||
                                    txt.contains("More"))
                    ) {
                        System.out.println(
                                "  -> tag: " + el.getTagName() + " | text: " + txt
                        );
                    }
                }
            } catch (Exception ignored) {}
            throw e;
        }

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        // Step 5: Confirm close
        try {
            System.out.println("[DEBUG closeBoard] Clicking closeConfirmBtn...");
            WebElement confirmBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(closeConfirmBtn)
            );
            System.out.println(
                    "[DEBUG closeBoard] closeConfirmBtn found — text: '" +
                            confirmBtn.getText() + "'"
            );
            safeClick(closeConfirmBtn);
            System.out.println("[DEBUG closeBoard] closeConfirmBtn clicked.");
        } catch (Exception e) {
            System.out.println("[DEBUG closeBoard] closeConfirmBtn click failed: " + e.getMessage());
            throw e;
        }

        // Step 6: Wait for closed message
        System.out.println("[DEBUG closeBoard] Waiting for closedBoardMessage...");
        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(closedBoardMessage)
            );
            System.out.println("[DEBUG closeBoard] Board closed successfully.");
        } catch (Exception e) {
            System.out.println("[DEBUG closeBoard] Timeout waiting for closedBoardMessage: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Returns true if the "This board is closed" screen is currently displayed.
     *
     * @return true if the closed board message is visible, false otherwise.
     */
    public boolean isClosedScreenDisplayed() {
        System.out.println("[DEBUG isClosedScreenDisplayed] Checking for closed screen...");
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement closedMsg = shortWait.until(
                    ExpectedConditions.visibilityOfElementLocated(closedBoardMessage)
            );
            boolean displayed = closedMsg.isDisplayed();
            System.out.println(
                    "[DEBUG isClosedScreenDisplayed] Closed message found — displayed: " + displayed
            );
            return displayed;
        } catch (Exception e) {
            System.out.println("[DEBUG isClosedScreenDisplayed] Closed screen not visible.");
            return false;
        }
    }

    /**
     * Reopens the currently closed board.
     */
    public void reopenBoard() {
        System.out.println("[DEBUG reopenBoard] Starting reopen board procedure...");

        System.out.println("[DEBUG reopenBoard] Printing all buttons/testid elements in DOM:");
        try {
            for (WebElement el : driver.findElements(
                    By.cssSelector("button, [data-testid]"))
            ) {
                String txt    = el.getText().trim();
                String testid = el.getAttribute("data-testid");
                if (testid != null ||
                        txt.contains("Reopen") ||
                        txt.contains("reopen")) {
                    System.out.println(
                            "  -> tag: " + el.getTagName() +
                                    " | text: '" + txt +
                                    "' | testid: " + testid +
                                    " | displayed: " + el.isDisplayed()
                    );
                }
            }
        } catch (Exception ignored) {}

        try {
            WebElement reopenBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(reopenBoardBtn)
            );
            System.out.println(
                    "[DEBUG reopenBoard] reopenBoardBtn found — text: '" +
                            reopenBtn.getText() + "'"
            );
            safeClick(reopenBoardBtn);
            System.out.println("[DEBUG reopenBoard] reopenBoardBtn clicked.");
        } catch (Exception e) {
            System.out.println("[DEBUG reopenBoard] reopenBoardBtn click failed: " + e.getMessage());
            throw e;
        }

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        System.out.println("[DEBUG reopenBoard] Printing all visible buttons after clicking reopen:");
        try {
            for (WebElement el : driver.findElements(
                    By.cssSelector("button, input[type='button'], input[type='submit']"))
            ) {
                if (el.isDisplayed()) {
                    System.out.println(
                            "  -> tag: " + el.getTagName() +
                                    " | text: '" + el.getText() +
                                    "' | data-testid: " + el.getAttribute("data-testid") +
                                    " | class: " + el.getAttribute("class")
                    );
                }
            }
        } catch (Exception ignored) {}

        // Check for a confirmation button in a popover
        try {
            By reopenConfirmBtn = By.xpath(
                    "//button[@data-testid='workspace-chooser-reopen-button']" +
                            " | //div[contains(@class,'popover')]//button[normalize-space(.)='Reopen']" +
                            " | //input[@value='Reopen']" +
                            " | //button[@data-testid='close-board-reopen-button-confirm']" +
                            " | //button[normalize-space(.)='Reopen']"
            );
            WebElement confirm = driver.findElement(reopenConfirmBtn);
            if (confirm.isDisplayed()) {
                System.out.println(
                        "[DEBUG reopenBoard] Confirm button found — text: '" +
                                confirm.getText() + "'. Clicking..."
                );
                confirm.click();
                System.out.println("[DEBUG reopenBoard] Reopen confirmed.");
            }
        } catch (Exception e) {
            System.out.println(
                    "[DEBUG reopenBoard] No confirmation button detected (likely reopened directly)."
            );
        }

        // Wait for reopen button to disappear — confirms closed overlay is gone
        System.out.println("[DEBUG reopenBoard] Waiting for reopenBoardBtn to disappear...");
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            longWait.until(
                    ExpectedConditions.invisibilityOfElementLocated(reopenBoardBtn)
            );
            System.out.println("[DEBUG reopenBoard] reopenBoardBtn is now invisible.");
        } catch (Exception e) {
            System.out.println(
                    "[DEBUG reopenBoard] Warning: reopenBoardBtn did not become invisible: " +
                            e.getMessage()
            );
        }

        // Wait for board title to confirm active board
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(boardTitleDisplay)
        );

        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        System.out.println("[DEBUG reopenBoard] Board reopened and settled.");
    }

    /**
     * Permanently deletes a closed board.
     */
    public void deleteBoardPermanently() {
        System.out.println("[DEBUG deleteBoardPermanently] Starting permanent delete...");

        System.out.println("[DEBUG deleteBoardPermanently] Printing elements containing 'delete'/'permanently':");
        try {
            for (WebElement el : driver.findElements(
                    By.cssSelector("a, button, p, span, div"))
            ) {
                String txt = el.getText().trim();
                if (txt.toLowerCase().contains("delete") ||
                        txt.toLowerCase().contains("permanently")) {
                    System.out.println(
                            "  -> tag: " + el.getTagName() +
                                    " | text: '" + txt +
                                    "' | testid: " + el.getAttribute("data-testid") +
                                    " | class: " + el.getAttribute("class")
                    );
                }
            }
        } catch (Exception ignored) {}

        try {
            WebElement deleteLink = wait.until(
                    ExpectedConditions.presenceOfElementLocated(permanentDeleteLink)
            );
            System.out.println(
                    "[DEBUG deleteBoardPermanently] permanentDeleteLink found — text: '" +
                            deleteLink.getText() + "'"
            );
            safeClick(permanentDeleteLink);
            System.out.println("[DEBUG deleteBoardPermanently] permanentDeleteLink clicked.");
        } catch (Exception e) {
            System.out.println(
                    "[DEBUG deleteBoardPermanently] permanentDeleteLink click failed: " +
                            e.getMessage()
            );
            throw e;
        }

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        try {
            WebElement confirmBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(boardDeleteConfirmBtn)
            );
            System.out.println(
                    "[DEBUG deleteBoardPermanently] boardDeleteConfirmBtn found — text: '" +
                            confirmBtn.getText() + "'"
            );
            safeClick(boardDeleteConfirmBtn);
            System.out.println("[DEBUG deleteBoardPermanently] boardDeleteConfirmBtn clicked.");
        } catch (Exception e) {
            System.out.println(
                    "[DEBUG deleteBoardPermanently] boardDeleteConfirmBtn click failed: " +
                            e.getMessage()
            );
            throw e;
        }
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Board Menu (Archive flow)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Opens the board side menu by clicking the menu button in the board header.
     */
    public void openBoardMenu() {
        WebElement menuBtn = wait.until(
                ExpectedConditions.elementToBeClickable(boardMenuButton)
        );
        menuBtn.click();
    }

    /**
     * Closes the Board Menu / Archived Items side panel by clicking
     * the "Close popover" button.
     * Uses invisibility wait after clicking to ensure the panel is
     * fully dismissed before the next action runs — prevents the
     * panel from blocking the "Show menu" button on the next call
     * to openBoardMenu().
     */
    public void closeBoardMenu() {
        try {
            WebElement closeButton = wait.until(
                    ExpectedConditions.elementToBeClickable(closePanelButton)
            );
            closeButton.click();
            System.out.println("[BoardPage] Board menu / panel closed.");

            // Wait for the panel to fully disappear before returning —
            // otherwise the very next openBoardMenu() call can still be
            // intercepted by the partially-visible panel.
            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(archivedItemPanel)
            );
            System.out.println("[BoardPage] Panel fully dismissed.");

        } catch (Exception e) {
            // Panel was already closed or never opened — safe to continue
            System.out.println(
                    "[BoardPage] closeBoardMenu: panel already closed or not found — skipping."
            );
        }
    }

    /**
     * Clicks the "Archived items" option from the open board menu.
     */
    public void openArchivedItems() {
        WebElement archivedItemsBtn = wait.until(
                ExpectedConditions.elementToBeClickable(archivedItemsOption)
        );
        archivedItemsBtn.click();
    }

    /**
     * Waits until the Archived Items panel is fully visible.
     */
    public void waitForArchivedItemsPanel() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(archivedItemsPanel)
        );
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Delete from Archived Items
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Waits until the specific archived card row appears inside the Archived Items panel.
     *
     * @param cardTitle The exact title of the card to wait for.
     */
    public void waitForArchivedCardToAppear(String cardTitle) {
        System.out.println(
                "  [BoardPage] Waiting for archived card '" + cardTitle + "' to appear in panel..."
        );
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        archivedCardItemLocator(cardTitle)
                )
        );
        System.out.println(
                "  [BoardPage] Archived card '" + cardTitle + "' is visible in panel."
        );
    }

    /**
     * Clicks the Delete button inside the archived card row for the given card title.
     * This triggers the confirmation popup.
     *
     * @param cardTitle The exact title of the card to delete.
     */
    public void clickDeleteButtonForArchivedCard(String cardTitle) {
        System.out.println(
                "  [BoardPage] Clicking Delete button for archived card: " + cardTitle
        );
        WebElement deleteBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        deleteButtonInsideArchivedCard(cardTitle)
                )
        );
        deleteBtn.click();
        System.out.println("  [BoardPage] Delete button clicked.");
    }

    /**
     * Clicks the confirm Delete button inside the confirmation popup
     * to permanently delete the archived card.
     */
    public void confirmCardDeletion(String cardTitle) {
        System.out.println("  [BoardPage] Waiting for delete confirmation button...");
        WebElement confirmBtn = wait.until(
                ExpectedConditions.elementToBeClickable(archivedCardDeleteConfirmBtn(cardTitle))
        );
        confirmBtn.click();
        System.out.println("  [BoardPage] Delete confirmed.");
    }

    /**
     * Returns true if the deleted card is NO LONGER listed in the Archived Items panel.
     *
     * @param cardTitle The exact title of the card to verify is gone.
     * @return true if card is absent from Archived Items; false if still present.
     */
    public boolean isCardDeletedFromArchivedItems(String cardTitle) {
        System.out.println(
                "  [BoardPage] Verifying card '" + cardTitle +
                        "' is deleted from Archived Items..."
        );
        List<WebElement> items = driver.findElements(archivedCardByTitle(cardTitle));

        // Case 1: Card is completely gone from the DOM
        if (items.isEmpty()) {
            System.out.println("  [BoardPage] Card not found in DOM — confirmed deleted.");
            return true;
        }

        // Case 2: Card element exists in DOM but is not displayed
        boolean notDisplayed = !items.get(0).isDisplayed();
        System.out.println(
                "  [BoardPage] Card in DOM — displayed: " + items.get(0).isDisplayed()
        );
        return notDisplayed;
    }
}