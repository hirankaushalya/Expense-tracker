import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.expensetracker_shops.Activities.Shop_details
import com.example.expensetracker_shops.R
import org.junit.Rule
import org.junit.Test

class ShopDetailsActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Shop_details::class.java)

    @Test
    fun testShopDetailsActivity() {
        // Wait for the activity to load
        Espresso.onView(ViewMatchers.withId(R.id.tvShopId))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Verify that the shop details are displayed correctly
        Espresso.onView(ViewMatchers.withId(R.id.tvShopName))
            .check(ViewAssertions.matches(withText("Shop Name")))

        Espresso.onView(ViewMatchers.withId(R.id.tvShopAddress))
            .check(ViewAssertions.matches(withText("Shop Address")))

        Espresso.onView(ViewMatchers.withId(R.id.tvShopMob))
            .check(ViewAssertions.matches(withText("Shop Mobile")))

        // Click the update button and verify that the dialog is displayed
        Espresso.onView(ViewMatchers.withId(R.id.btnUpdate))
            .perform(ViewActions.click())

        Espresso.onView(withText("Updating Shop Name Record"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Enter new values in the update dialog and click the update button
        Espresso.onView(ViewMatchers.withId(R.id.upShopName))
            .perform(ViewActions.replaceText("New Shop Name"))

        Espresso.onView(ViewMatchers.withId(R.id.upShopAddress))
            .perform(ViewActions.replaceText("New Shop Address"))

        Espresso.onView(ViewMatchers.withId(R.id.upShopMob))
            .perform(ViewActions.replaceText("New Shop Mobile"))

        Espresso.onView(ViewMatchers.withId(R.id.btnUpdateData))
            .perform(ViewActions.click())

        // Verify that the updated values are displayed
        Espresso.onView(ViewMatchers.withId(R.id.tvShopName))
            .check(ViewAssertions.matches(withText("New Shop Name")))

        Espresso.onView(ViewMatchers.withId(R.id.tvShopAddress))
            .check(ViewAssertions.matches(withText("New Shop Address")))

        Espresso.onView(ViewMatchers.withId(R.id.tvShopMob))
            .check(ViewAssertions.matches(withText("New Shop Mobile")))

        // Click the delete button and verify that the shop is deleted
        Espresso.onView(ViewMatchers.withId(R.id.btnDelete))
            .perform(ViewActions.click())

        Espresso.onView(withText("Shop data deleted"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
