package ch.epfl.sweng.ctf.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ch.epfl.sweng.ctf.R
import ch.epfl.sweng.ctf.fragments.ChallengesListFragment
import ch.epfl.sweng.ctf.fragments.HomeFragment
import ch.epfl.sweng.ctf.fragments.LeaderboardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Entry point of the application
 * Initializes main layout and starting logic
 *
 * @author Alexandre Chau
 */
class MainActivity : AppCompatActivity() {
    private val bottomNavigationMenu: BottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation_menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // load home fragment on newly generated instance
            showFragment<HomeFragment>()
        }

        bottomNavigationMenu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> showFragment<HomeFragment>()
                R.id.menu_challenges -> showFragment<ChallengesListFragment>()
                R.id.menu_leaderboard -> showFragment<LeaderboardFragment>()
                else -> false
            }
        }

        // force refresh of back stack fragment
        supportFragmentManager.addOnBackStackChangedListener {
            val backFragment = supportFragmentManager.findFragmentById(R.id.main_fragment)
            backFragment?.onResume()
        }
    }

    // append app bar action menu to top
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_overflow_menu, menu)
        return true
    }

    // handle app bar action items
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            // close fragment on top if any
            val sfm = supportFragmentManager
            if (sfm.backStackEntryCount > 0) {
                sfm.popBackStack()
            }
            true
        }
        R.id.action_settings -> {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    // Helper function to update shown fragment on this activity
    private inline fun <reified F : Fragment> showFragment(): Boolean {
        // Clear all fragments in back stack
        val sfm = supportFragmentManager
        repeat(sfm.backStackEntryCount) { sfm.popBackStack() }
        // Update fragment
        sfm.commit {
            setReorderingAllowed(true)
            replace<F>(R.id.main_fragment)
        }
        return true
    }
}