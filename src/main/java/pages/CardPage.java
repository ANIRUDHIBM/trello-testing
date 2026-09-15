package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import utils.TestData;

public class CardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ─────────────────────────────────────────────────────────────────────────
    // LOCATORS — Anirudh
    // ─────────────────────────────────────────────────────────────────────────

    private By myTrelloBoard = By.xpath(
            "//a[@href='/b/d8xcq3jv/my-trello-board'" +
                    " and @title='My Trello Board']"
    );

    private By addCardButton = By.xpath(
            "//button[@data-testid='list-add-card-button'" +
                    " and @aria-label='Add a card in To Do']"
    );

    private By cardTitleTextarea = By.xpath(
            "//div[@data-testid='list-card-composer-textarea']"
    );

    private By addCardSubmitButton = By.xpath(
            "//button[@data-testid='list-card-composer-add-card-button']"
    );

    // ─────────────────────────────────────────────────────────────────────────
    // LOCATORS — Card Back Modal
    // ─────────────────────────────────────────────────────────────────────────
    private final By cardBackModal =
            By.cssSelector("[data-testid='card-back-panel']");
    private final By cardBackHeader =
            By.cssSelector("[data-testid='card-back-header']");
    private final By cardBackTitleInput =
            By.cssSelector("[data-testid='card-back-title-input']");
    private final By actionsButton =
            By.cssSelector("[data-testid='card-back-actions-button']");
    private final By cardDoneStateButton =
            By.cssSelector("[data-testid='card-done-state-completion-button']");
    private final By archiveCardOption = By.xpath(
            "//button[contains(.,'Archive')" +
                    " or .//span[normalize-space()='Archive']]"
    );

    // Dynamic locators — Anirudh
    private By cardNameLocator(String cardTitle) {
        return By.xpath(
                "//a[@data-testid='card-name' and text()='" + cardTitle + "']"
        );
    }

    private By archivedCardLocator(String cardTitle) {
        return By.xpath(
                "//div[@data-testid='archived-card']" +
                        "//a[@data-testid='card-name' and normalize-space()='" + cardTitle + "']"
        );
    }

    //these are the declarations which are made by harshit
    private By descriptionButton = By.cssSelector("button[data-testid='description-button']");
    private By editDescriptionButton = By.cssSelector("button[aria-label='Edit description']");
    private By descriptionField = By.id("ak-editor-textarea");
    private By descriptionSaveButton = By.cssSelector("button[data-testid='description-save-button']");
    private By labelsButton = By.xpath("//button[normalize-space()='Labels']");
    private By closeCardButton = By.cssSelector("button[aria-label='Close dialog']");
    private By datesButton = By.cssSelector("button[data-testid='card-back-due-date-button']");
    private By saveDateButton = By.cssSelector("button[data-testid='save-date-button']");
    private By dueDateField = By.cssSelector("input[data-testid='due-date-field']");
    private By checklistButton = By.xpath("//button[normalize-space()='Checklist']");
    private By checklistTitleField = By.id("id-checklist");
    private By checklistAddButton = By.cssSelector("button[data-testid='checklist-add-button']");
    private By checklistItemInput = By.cssSelector("textarea[data-testid='check-item-name-input']");
    private By checklistItemAddButton = By.cssSelector("button[data-testid='check-item-add-button']");

    private By addToCardButton = By.xpath("//button[@aria-label='Add to card']");
    private By attachmentButton = By.cssSelector("[data-testid='card-back-attachment-button']");
    private By attachLinkInput = By.cssSelector("input[data-testid='link-url']");
    private By attachLinkSubmitButton = By.cssSelector("[data-testid='link-picker-insert-button']");
    private By attachmentsListItem = By.cssSelector("[data-testid='attachment-links-list'] li");

    private By coverButton = By.cssSelector("[data-testid='card-back-cover-button']");
    private By coverColorSwatch = By.cssSelector("[data-testid^='color-tile-']");
    private By coverAppliedIndicator = By.cssSelector("[data-testid='card-cover']");

    // Dynamic locators — Harshit
    private By checklistItemCheckboxInput(String itemName) {
        return By.xpath(
                "//input[@type='checkbox' and @aria-label='" + itemName + "']"
        );
    }

    /**
     * The checkbox <input> is visually hidden behind its wrapping label - Chrome redirects
     * any click aimed at the input to this label ("element would receive the click"), and
     * clicking the input directly via JS does not trigger Trello's React toggle handler at
     * all. This label is the real, reliable click target.
     */
    private By checklistItemCheckboxLabel(String itemName) {
        return By.xpath(
                "//label[@data-testid='clickable-checkbox']" +
                        "[.//input[@aria-label='" + itemName + "']]"
        );
    }


    // ─────────────────────────────────────────────────────────────────────────
    // CONSTRUCTOR
    // ─────────────────────────────────────────────────────────────────────────

    public CardPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Anirudh
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Clicks on "My Trello Board" from the dashboard.
     */
    public void clickMyTrelloBoard() {
        System.out.println("STEP: Clicking on 'My Trello Board'...");

        WebElement board = wait.until(
                ExpectedConditions.elementToBeClickable(myTrelloBoard)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", board);

        board.click();

        wait.until(ExpectedConditions.urlContains("my-trello-board"));
        System.out.println("STEP: 'My Trello Board' opened successfully.");
    }

    /**
     * Clicks on "Add a card" button in the list defined by TestData.listName.
     */
    public void clickAddCardButton() {
        By addCardButton = By.xpath(
                "//button[@aria-label='Add a card in " + TestData.listName + "']"
        );

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(addCardButton)
        );

        button.click();
        System.out.println("Button Got Clicked");
    }

    /**
     * Enters the card title in the composer textarea.
     *
     * @param cardTitle Title of the card to be created.
     */
    public void enterCardTitle(String cardTitle) {
        System.out.println("STEP: Entering card title: " + cardTitle);

        WebElement titleBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(cardTitleTextarea)
        );

        titleBox.clear();
        titleBox.sendKeys(cardTitle);
        System.out.println("STEP: Card title entered: " + cardTitle);
    }

    /**
     * Clicks the "Add card" submit button to save the card.
     */
    public void clickAddCardSubmit() {
        System.out.println("STEP: Clicking 'Add card' submit button...");

        wait.until(
                ExpectedConditions.elementToBeClickable(addCardSubmitButton)
        ).click();

        System.out.println("STEP: Card submitted successfully.");
    }

    /**
     * Verifies if the card is visible on the board.
     *
     * @param cardTitle Title of the card to verify.
     * @return true if card is found and visible, false otherwise.
     */
    public boolean isCardCreated(String cardTitle) {
        System.out.println("STEP: Verifying card '" + cardTitle + "' is created...");

        try {
            WebElement card = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//a[@data-testid='card-name'" +
                                            " and contains(text(),'" + cardTitle + "')]"
                            )
                    )
            );

            boolean isVisible = card.isDisplayed();
            System.out.println("STEP: Card visible on board: " + isVisible);
            return isVisible;

        } catch (Exception e) {
            System.out.println("❌ Card NOT found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Waits until the card detail modal is fully open.
     */
    public void waitForCardModalToOpen() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cardBackModal)
        );
    }

    /**
     * Waits until the card detail modal has fully closed.
     */
    public void waitForCardModalToClose() {
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(cardBackModal)
        );
    }

    /**
     * Clicks the Actions button inside the open card detail modal.
     */
    public void clickActionsButton() {
        WebElement actionsBtn = wait.until(
                ExpectedConditions.elementToBeClickable(actionsButton)
        );
        actionsBtn.click();
    }

    /**
     * Clicks the Archive option from inside the Actions menu.
     */
    public void clickArchiveFromActions() {
        WebElement archiveOption = wait.until(
                ExpectedConditions.elementToBeClickable(archiveCardOption)
        );
        archiveOption.click();
    }

    /**
     * Returns true if the card is currently visible on the board.
     *
     * @param cardTitle Title of the card to check.
     * @return true if visible, false otherwise.
     */
    public boolean isCardVisibleOnBoard(String cardTitle) {
        List<WebElement> cards = driver.findElements(cardNameLocator(cardTitle));
        return !cards.isEmpty() && cards.get(0).isDisplayed();
    }

    /**
     * Returns true if the archived card appears in the Archived Items panel.
     *
     * @param cardTitle Title of the archived card to look for.
     * @return true if listed, false otherwise.
     */
    public boolean isArchivedCardListed(String cardTitle) {

        By anyArchivedCard = By.xpath("//div[@data-testid='archived-card']");

        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(anyArchivedCard)
            );
        } catch (TimeoutException e) {
            System.out.println(
                    "❌ No archived cards loaded in panel within timeout."
            );
            return false;
        }

        List<WebElement> archivedCards =
                driver.findElements(archivedCardLocator(cardTitle));

        for (WebElement card : archivedCards) {
            if (card.isDisplayed()) {
                return true;
            }
        }
        return false;
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Harshit
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Opens a card by clicking its title on the board.
     * The click occasionally doesn't open the modal (board re-render timing) -
     * retry with a JS-click fallback rather than failing outright, same pattern
     * used for DashboardPage.openBoard().
     *
     * @param cardTitle Exact title of the card to open.
     */
    public void openCard(String cardTitle) {

        By card = By.xpath(
                "//*[@data-testid='card-name'" +
                        " and normalize-space()='" + cardTitle + "']"
        );
        By closeDialogButton = By.cssSelector("button[aria-label='Close dialog']");

        for (int attempt = 1; attempt <= 2; attempt++) {
            WebElement cardElement = wait.until(
                    ExpectedConditions.elementToBeClickable(card)
            );
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    cardElement
            );
            try {
                cardElement.click();
            } catch (StaleElementReferenceException e) {
                System.out.println(
                        "STEP: Element went stale before click, re-fetching..."
                );
                cardElement = wait.until(               // ← re-fetch fresh reference
                        ExpectedConditions.elementToBeClickable(card)
                );
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();",
                        cardElement                     // ← uses live element
                );
            }catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();",
                        cardElement
                );
            }
            try {
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(closeDialogButton)
                );
                System.out.println("STEP: Card opened successfully: " + cardTitle);
                return;
            } catch (TimeoutException e) {
                if (attempt == 2) {
                    throw e;
                }
                System.out.println(
                        "STEP: Card modal did not open on attempt " + attempt + ", retrying..."
                );
            }
        }
    }

    /**
     * Clicks the Description button inside the open card modal.
     * Once a description is already saved, the "Add a more detailed description"
     * button is replaced by an "Edit description" button — picks whichever is present.
     */
    public void clickDescription() {
        System.out.println("STEP: Clicking Description...");

        List<WebElement> addButton = driver.findElements(descriptionButton);
        By target = (!addButton.isEmpty() && addButton.get(0).isDisplayed())
                ? descriptionButton
                : editDescriptionButton;

        WebElement description = wait.until(
                ExpectedConditions.elementToBeClickable(target)
        );
        description.click();

        System.out.println("STEP: Description opened.");
    }

    /**
     * Types the given description text into the description editor field.
     * The field may already contain previously-saved text (Edit flow on a reused
     * fixture card) — select-all + delete first so new text does not interleave.
     *
     * @param description The description text to enter.
     */
    public void addDescription(String description) {

        WebElement field = wait.until(
                ExpectedConditions.elementToBeClickable(descriptionField)
        );

        field.click();
        field.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        field.sendKeys(description);
    }

    /**
     * Returns true if the description editor field contains the expected text.
     *
     * @param expectedDescription The description text to verify.
     * @return true if text matches, false otherwise.
     */
    public boolean isDescriptionEntered(String expectedDescription) {

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(descriptionField)
        );

        String actualDescription = field.getText();

        System.out.println("Expected Description: " + expectedDescription);
        System.out.println("Actual Description:   " + actualDescription);

        return actualDescription.trim().equals(expectedDescription.trim());
    }

    /**
     * Clicks the Save button to persist the description.
     */
    public void savedescription() {
        System.out.println("Button clicked and yet to save");
        WebElement saveButton = wait.until(
                ExpectedConditions.elementToBeClickable(descriptionSaveButton)
        );
        saveButton.click();
        System.out.println("the button gotclicked and save");
    }

    /**
     * Returns true if the saved description content area displays the expected text.
     *
     * @param expectedDescription The description text to verify after saving.
     * @return true if text matches, false otherwise.
     */
    public boolean isSavedDescriptionDisplayed(String expectedDescription) {

        By savedDescription = By.cssSelector("[data-testid='description-content-area']");

        try {
            WebElement description = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(savedDescription)
            );

            String actualDescription = description.getText();

            System.out.println("Expected saved description: " + expectedDescription);
            System.out.println("Actual saved description:   " + actualDescription);

            return actualDescription.trim().equals(expectedDescription.trim());

        } catch (Exception e) {
            System.out.println("Saved description not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Clicks the Labels button inside the open card modal using JS click
     * to avoid overlay interception issues.
     */
    public void clickLabels() {
        System.out.println("STEP: Clicking Labels...");

        WebElement labels = wait.until(
                ExpectedConditions.presenceOfElementLocated(labelsButton)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                labels
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                labels
        );

        System.out.println("STEP: Labels menu opened.");
    }

    /**
     * Selects a label by its color name from the Labels menu.
     *
     * @param color The color name (e.g. "green", "red") of the label to select.
     */
    public void selectLabel(String color) {
        System.out.println("STEP: Selecting label: " + color);

        By label = By.cssSelector(
                "span[data-testid='card-label'][data-color='" + color + "']"
        );

        WebElement labelElement = wait.until(
                ExpectedConditions.elementToBeClickable(label)
        );

        labelElement.click();

        System.out.println("STEP: Label selected: " + color);
    }
    /**
     * Get the "Add a card" button element for the current fixture list, for layout/rendering assertions.
     */
    public WebElement getAddCardButtonElement() {
        By addCardBtn = By.xpath("//button[@aria-label='Add a card in " + TestData.listName + "']");
        return wait.until(ExpectedConditions.elementToBeClickable(addCardBtn));
    }

    /**
     * Get the card dialog's close button element, for layout/rendering assertions.
     */
    public WebElement getCloseCardButtonElement() {
        return wait.until(ExpectedConditions.elementToBeClickable(closeCardButton));
    }

    /**
     * Get the description area/button element (whichever of add/edit is currently present),
     * for layout/rendering assertions.
     */
    public WebElement getDescriptionAreaElement() {
        List<WebElement> addButton = driver.findElements(descriptionButton);
        By target = (!addButton.isEmpty() && addButton.get(0).isDisplayed())
                ? descriptionButton
                : editDescriptionButton;
        return wait.until(ExpectedConditions.visibilityOfElementLocated(target));
    }

    public void closeCard() {
        System.out.println("STEP: Closing card...");

        WebElement closeButton = wait.until(
                ExpectedConditions.elementToBeClickable(closeCardButton)
        );

        closeButton.click();

        // Wait for the dialog to actually finish closing - otherwise the overlay can
        // still intercept the very next click (e.g. reopening the same card immediately).
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(closeCardButton)
        );

        System.out.println("STEP: Card closed successfully.");
    }

    /**
     * Returns true if the given label color is applied and visible on the card tile.
     *
     * @param color The color name to check.
     * @return true if the label is displayed on the card, false otherwise.
     */
    public boolean isLabelApplied(String color) {

        By appliedLabel = By.cssSelector(
                "button[data-testid='compact-card-label'][data-color='" + color + "']"
        );

        try {
            WebElement label = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(appliedLabel)
            );

            boolean displayed = label.isDisplayed();
            System.out.println("STEP: Label '" + color + "' displayed on card: " + displayed);
            return displayed;

        } catch (Exception e) {
            System.out.println("STEP: Label '" + color + "' not applied.");
            return false;
        }
    }

    /**
     * Applies the label only if it is not already applied.
     * Selecting an already-applied label toggles it OFF in Trello, so a plain
     * selectLabel() is not safe to call twice on a reused fixture card.
     *
     * @param color The color name of the label to apply.
     */
    public void ensureLabelApplied(String color) {
        if (isLabelApplied(color)) {
            System.out.println("STEP: Label '" + color + "' already applied, skipping.");
            return;
        }
        clickLabels();
        selectLabel(color);
    }

    /**
     * Clicks the already-applied label badge on the card tile via JS click.
     *
     * @param color The color name of the applied label to click.
     */
    public void clickAppliedLabel(String color) {
        System.out.println("STEP: Clicking applied label: " + color);

        By appliedLabel = By.cssSelector(
                "button[data-testid='compact-card-label'][data-color='" + color + "']"
        );

        WebElement label = wait.until(
                ExpectedConditions.elementToBeClickable(appliedLabel)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                label
        );

        System.out.println("STEP: Applied label clicked: " + color);
    }

    /**
     * Clicks the Dates button inside the open card modal.
     * Once a due date is already set, the "Dates" quick-action button is replaced by
     * a due-date badge button — picks whichever is actually present.
     */
    public void clickDates() {
        System.out.println("STEP: Clicking Dates...");

        By datesQuickButton = By.xpath("//button[normalize-space()='Dates']");
        By dueDateBadge     = By.cssSelector(
                "button[data-testid='due-date-badge-with-date-range-picker']"
        );

        List<WebElement> quick = driver.findElements(datesQuickButton);
        By target = (!quick.isEmpty() && quick.get(0).isDisplayed())
                ? datesQuickButton
                : dueDateBadge;

        WebElement dates = wait.until(
                ExpectedConditions.presenceOfElementLocated(target)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                dates
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                dates
        );

        System.out.println("STEP: Dates menu opened.");
    }

    /**
     * Clears and enters the given due date string into the due date input field.
     *
     * @param dueDate The due date string to enter (e.g. "09/15/2026").
     */
    public void enterDueDate(String dueDate) {
        System.out.println("STEP: Entering due date: " + dueDate);

        WebElement dateField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(dueDateField)
        );

        dateField.click();
        dateField.clear();
        dateField.sendKeys(dueDate);

        System.out.println("STEP: Due date entered: " + dueDate);
    }

    /**
     * Clicks the Save button to persist the due date.
     */
    public void saveDueDate() {
        System.out.println("STEP: Saving due date...");

        WebElement saveButton = wait.until(
                ExpectedConditions.elementToBeClickable(saveDateButton)
        );

        saveButton.click();

        System.out.println("STEP: Due date saved successfully.");
    }

    /**
     * Returns true if the due date badge on the card contains the expected date string.
     *
     * @param expectedDate The date string to look for in the badge text.
     * @return true if the badge text contains the expected date, false otherwise.
     */
    public boolean isDueDateDisplayed(String expectedDate) {

        By savedDueDate = By.cssSelector(
                "button[data-testid='due-date-badge-with-date-range-picker']"
        );

        try {
            WebElement dueDate = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(savedDueDate)
            );

            String actualDate = dueDate.getText();

            System.out.println("Expected Due Date: " + expectedDate);
            System.out.println("Actual Due Date:   " + actualDate);

            return actualDate.contains(expectedDate);

        } catch (Exception e) {
            System.out.println("Due date badge not found: " + e.getMessage());
            return false;
        }
    }

    /**
     * Clicks the Checklist button inside the open card modal.
     */
    public void clickChecklist() {
        System.out.println("STEP: Clicking Checklist...");

        WebElement checklist = wait.until(
                ExpectedConditions.elementToBeClickable(checklistButton)
        );

        checklist.click();

        System.out.println("STEP: Checklist menu opened.");
    }

    /**
     * Clears and enters the checklist name in the checklist title field.
     *
     * @param checklistName The name to give the new checklist.
     */
    public void enterChecklistName(String checklistName) {
        System.out.println("STEP: Entering checklist name: " + checklistName);

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(checklistTitleField)
        );

        field.click();
        field.clear();
        field.sendKeys(checklistName);

        System.out.println("STEP: Checklist name entered: " + checklistName);
    }

    /**
     * Clicks the Add button to create the checklist.
     */
    public void addChecklist() {
        System.out.println("STEP: Clicking Add checklist...");

        WebElement addButton = wait.until(
                ExpectedConditions.elementToBeClickable(checklistAddButton)
        );

        addButton.click();

        System.out.println("STEP: Checklist added successfully.");
    }

    /**
     * Clears and enters the given item name into the checklist item input field.
     *
     * @param itemName The name of the checklist item to add.
     */
    public void enterChecklistItem(String itemName) {
        System.out.println("STEP: Entering checklist item: " + itemName);

        WebElement itemField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(checklistItemInput)
        );

        itemField.click();
        itemField.clear();
        itemField.sendKeys(itemName);

        System.out.println("STEP: Checklist item entered: " + itemName);
    }

    /**
     * Clicks the Add button to submit the checklist item.
     */
    public void addChecklistItem() {
        System.out.println("STEP: Clicking Add checklist item...");

        WebElement addButton = wait.until(
                ExpectedConditions.elementToBeClickable(checklistItemAddButton)
        );

        addButton.click();

        System.out.println("STEP: Checklist item added successfully.");
    }

    /**
     * Get an existing checklist item's clickable checkbox row, for layout/rendering
     * assertions - unlike the "add item" composer (which collapses on a fresh card
     * open and needs an extra click to reveal), this row is always visible once the
     * item exists, making it a stable assertion target regardless of composer state.
     */
    public WebElement getChecklistItemCheckboxElement(String itemName) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(checklistItemCheckboxLabel(itemName)));
    }


    /**
     * Returns true if the checklist item with the given name is visible.
     *
     * @param expectedItem The checklist item name to look for.
     * @return true if the item is displayed, false otherwise.
     */
    public boolean isChecklistItemDisplayed(String expectedItem) {

        By checkboxInput = checklistItemCheckboxInput(expectedItem);

        try {
            WebElement item = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(checkboxInput)
            );
            return item.isDisplayed();
        } catch (Exception e) {
            System.out.println(
                    "Checklist item '" + expectedItem + "' not found: " + e.getMessage()
            );
            return false;
        }
    }

    /**
     * Checks whether a checklist with the given name already exists on the card.
     * Used to avoid creating duplicate checklists when the fixture card is reused across runs.
     * Uses contains() rather than an exact match — the title element's accessible text
     * includes a hidden "Checklist" prefix concatenated with the visible name.
     *
     * @param checklistName The checklist name to check for.
     * @return true if the checklist heading is present, false otherwise.
     */
    public boolean isChecklistPresent(String checklistName) {
        By checklistHeading = By.xpath(
                "//h3[@data-testid='checklist-title']" +
                        "[contains(normalize-space(), '" + checklistName + "')]"
        );
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            return shortWait.until(
                    ExpectedConditions.visibilityOfElementLocated(checklistHeading)
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks the checklist item only if it is not already checked.
     * Re-clicking an already-checked checkbox toggles it back off — this guard
     * prevents that from happening when the fixture card is reused across test reruns.
     *
     * @param itemName The checklist item name to check.
     */
    public void checkChecklistItem(String itemName) {

        if (isChecklistItemChecked(itemName)) {
            System.out.println(
                    "STEP: Checklist item '" + itemName + "' already checked, skipping."
            );
            return;
        }

        System.out.println("STEP: Checking checklist item: " + itemName);

        WebElement label = wait.until(
                ExpectedConditions.elementToBeClickable(
                        checklistItemCheckboxLabel(itemName)
                )
        );

        label.click();

        // The click is optimistic-UI, not instant — wait for the checkbox to actually
        // flip rather than assuming the click landed by the time we return.
        wait.until(
                d -> d.findElement(checklistItemCheckboxInput(itemName)).isSelected()
        );

        System.out.println("STEP: Checklist item checked: " + itemName);
    }

    /**
     * Returns true if the checklist item with the given name is currently checked.
     * Uses isSelected() to reflect the live checked property reliably.
     *
     * @param itemName The checklist item name to inspect.
     * @return true if checked, false otherwise.
     */
    public boolean isChecklistItemChecked(String itemName) {

        WebElement checkbox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        checklistItemCheckboxInput(itemName)
                )
        );

        // .isSelected() reflects the live checked property - unlike scraping class names
        // or innerHTML, which are unreliable.
        boolean checked = checkbox.isSelected();

        System.out.println(
                "STEP: Checklist item '" + itemName + "' checked: " + checked
        );

        return checked;
    }

    /**
     * Waits until the Labels button is clickable again after a previous interaction.
     */
    public void waitForLabelsButton() {
        By labelsButton = By.xpath("//button[normalize-space()='Labels']");

        wait.until(ExpectedConditions.elementToBeClickable(labelsButton));

        System.out.println("STEP: Labels button is ready again.");
    }

    /**
     * Debug helper — prints all accessible attributes of the green label element.
     */
    public void debugGreenLabel() {

        By greenLabel = By.cssSelector(
                "span[data-testid='card-label'][data-color='green']"
        );

        WebElement label = wait.until(
                ExpectedConditions.visibilityOfElementLocated(greenLabel)
        );

        System.out.println("===== GREEN LABEL DEBUG =====");
        System.out.println("Tag:         " + label.getTagName());
        System.out.println("Text:        " + label.getText());
        System.out.println("Class:       " + label.getAttribute("class"));
        System.out.println("Aria-label:  " + label.getAttribute("aria-label"));
        System.out.println("Aria-checked:" + label.getAttribute("aria-checked"));
        System.out.println("Data-color:  " + label.getAttribute("data-color"));
        System.out.println("Data-testid: " + label.getAttribute("data-testid"));
        System.out.println("Role:        " + label.getAttribute("role"));
        System.out.println("HTML:        " + label.getAttribute("outerHTML"));
        System.out.println("============================");
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Harshit: Attachments (link attachment)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Opens the "Add to card" menu and then clicks the Attachment option.
     */
    public void clickAttachment() {
        System.out.println("STEP: Opening 'Add to card' menu...");
        clickWithRetry(addToCardButton);

        System.out.println("STEP: Clicking Attachment...");
        clickWithRetry(attachmentButton);
        System.out.println("STEP: Attachment menu opened.");
    }

    /**
     * Scrolls the element into view and clicks it, retrying once with a JS-click fallback
     * if the first attempt throws (e.g. a transient overlay/re-render intercepts the click).
     * Same defensive pattern used for openBoard()/openCard() to survive React re-render timing.
     *
     * @param locator The By locator of the element to click.
     */
    private void clickWithRetry(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    element
            );
        }
    }

    /**
     * Types the given URL into the attachment link input field.
     *
     * @param url The URL to attach.
     */
    public void attachLink(String url) {
        System.out.println("STEP: Attaching link: " + url);

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(attachLinkInput)
        );

        field.click();
        field.clear();
        field.sendKeys(url);
    }

    /**
     * Clicks the submit button to confirm the link attachment.
     */
    public void confirmAttachLink() {
        System.out.println("STEP: Confirming link attachment...");

        WebElement submit = wait.until(
                ExpectedConditions.elementToBeClickable(attachLinkSubmitButton)
        );

        submit.click();
        System.out.println("STEP: Link attachment submitted.");
    }

    /**
     * Returns true if an attachment matching the given URL or name fragment is already present.
     * Used to avoid re-attaching the same link when the fixture card is reused across runs.
     *
     * @param urlOrNameFragment A substring of the URL or display name to search for.
     * @return true if a matching attachment is found, false otherwise.
     */
    public boolean isAttachmentPresent(String urlOrNameFragment) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return shortWait.until(d -> {
                for (WebElement el : d.findElements(attachmentsListItem)) {
                    String text = el.getText();
                    String href = el.getAttribute("href");
                    if ((text != null && text.contains(urlOrNameFragment))
                            || (href != null && href.contains(urlOrNameFragment))) {
                        return true;
                    }
                }
                return false;
            });
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Attaches the given link only if it is not already present on the card.
     *
     * @param url The URL to attach.
     */
    public void ensureLinkAttached(String url) {
        if (isAttachmentPresent(url)) {
            System.out.println("STEP: Attachment '" + url + "' already present, skipping.");
            return;
        }
        clickAttachment();
        attachLink(url);
        confirmAttachLink();
    }


    // ─────────────────────────────────────────────────────────────────────────
    // METHODS — Harshit: Cover (solid color)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Clicks the Cover button inside the open card modal.
     */
    public void clickCover() {
        System.out.println("STEP: Clicking Cover...");

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(coverButton)
        );

        button.click();
        System.out.println("STEP: Cover menu opened.");
    }

    /**
     * Selects the first available cover color swatch and dismisses the popover.
     * The cover popover stays open after picking a color and overlaps the card
     * dialog's own controls — dismiss with Escape after clicking.
     */
    public void selectCoverColor() {
        System.out.println("STEP: Selecting a cover color...");

        WebElement swatch = wait.until(
                ExpectedConditions.elementToBeClickable(coverColorSwatch)
        );

        swatch.click();
        System.out.println("STEP: Cover color applied.");

        new org.openqa.selenium.interactions.Actions(driver)
                .sendKeys(org.openqa.selenium.Keys.ESCAPE)
                .perform();
    }

    /**
     * Returns true if the cover indicator element is visible on the open card modal.
     *
     * @return true if a cover is applied, false otherwise.
     */
    public boolean isCoverApplied() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return shortWait.until(
                    ExpectedConditions.visibilityOfElementLocated(coverAppliedIndicator)
            ).isDisplayed();
        } catch (Exception e) {
            System.out.println("Cover indicator not found: " + e.getMessage());
            return false;
        }
    }
    public void ensureCoverApplied() {
        if (isCoverApplied()) {
            System.out.println("STEP: Cover already applied, skipping.");
            return;
        }
        clickCover();
        selectCoverColor();
    }

    /**
     * Get every cover color swatch in the open cover popover, for layout/rendering
     * assertions (a color grid is exactly the kind of element that clips at narrow
     * viewport widths without necessarily causing page-level horizontal overflow).
     */
    public List<WebElement> getCoverColorSwatchElements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(coverColorSwatch));
        return driver.findElements(coverColorSwatch);
    }

    /**
     * Dismisses the cover color popover without picking a color - same Escape-key
     * approach used internally by selectCoverColor() once a color has been applied.
     */
    public void closeCoverPopover() {
        new org.openqa.selenium.interactions.Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
}