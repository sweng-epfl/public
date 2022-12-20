package ch.epfl.sweng.ctf.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ch.epfl.sweng.ctf.R
import ch.epfl.sweng.ctf.let
import ch.epfl.sweng.ctf.models.RankedUser
import ch.epfl.sweng.ctf.repositories.ChallengesRepository
import ch.epfl.sweng.ctf.repositories.FlagsRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

/**
 * Leaderboard fragment
 * Displays current CTF leaderboard
 *
 * @author Alexandre Chau
 */
class LeaderboardFragment : Fragment() {
    private val leaderboardListView: ListView? by lazy { view?.findViewById(R.id.leaderboard_list) }
    private val flagsRepository: FlagsRepository by inject()
    private val challengesRepository: ChallengesRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.leaderboard_change_username_button).setOnClickListener {
            changeUsername()
        }
        view.findViewById<Button>(R.id.leaderboard_refresh_button).setOnClickListener {
            syncLeaderboard()
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let {
            syncUser(it)
            syncLeaderboard()
        }
    }

    private fun changeUsername() {
        let(context, view) { ctx, view ->
            val viewGroup = view.findViewById<ViewGroup>(R.id.fragment_leaderboard)
            val dialogView =
                layoutInflater.inflate(R.layout.dialog_change_username, viewGroup, false)
            val editText = dialogView.findViewById<EditText>(R.id.dialog_change_username)

            MaterialAlertDialogBuilder(ctx)
                .setTitle(ctx.getString(R.string.change_username))
                .setView(dialogView)
                .setNeutralButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Change") { dialog, _ ->
                    val newName = editText.text.toString()
                    val newUser = flagsRepository.getCurrentUser().withName(newName)
                    flagsRepository.saveCurrentUser(newUser)
                    syncUser(view)
                    syncLeaderboard(true)
                }
                .show()
        }
    }

    private fun syncUser(view: View) {
        context?.let { ctx ->
            val user = flagsRepository.getCurrentUser()
            view.findViewById<TextView>(R.id.leaderboard_username).text = user.name
            view.findViewById<TextView>(R.id.leaderboard_points).text =
                ctx.getString(R.string.score_points, challengesRepository.getCurrentScore())
        }
    }

    private fun syncLeaderboard(force: Boolean = false) {
        flagsRepository.uploadMissing(challengesRepository, force)
        flagsRepository.getLeaderboard()
            .thenApply { rankedUsers ->
                val adapter =
                    context?.let {
                        LeaderboardListViewAdapter(
                            it,
                            R.id.leaderboard_list,
                            rankedUsers
                        )
                    }
                leaderboardListView?.adapter = adapter
                adapter?.notifyDataSetChanged()
            }
            .exceptionally {
                view?.let { view ->
                    Snackbar.make(
                        view,
                        "Could not fetch leaderboard",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    /**
     * Collection adapter between list of ranked users models and ListView display
     */
    private inner class LeaderboardListViewAdapter(
        private val ctx: Context,
        res: Int,
        private val rankedUsers: List<RankedUser>
    ) : ArrayAdapter<RankedUser>(ctx, res, rankedUsers) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val card = convertView ?: layoutInflater.inflate(
                R.layout.list_item_leaderboard,
                leaderboardListView,
                false
            )
            val rankedUser = rankedUsers[position]

            val imgRes = when (position) {
                0 -> R.drawable.icon_cup_gold
                1 -> R.drawable.icon_cup_silver
                2 -> R.drawable.icon_cup_bronze
                else -> null
            }
            val imgView = card.findViewById<ImageView>(R.id.leaderboard_item_image)
            val drawable = imgRes?.let { res -> ContextCompat.getDrawable(ctx, res) }
            imgView.setImageDrawable(drawable)

            card.findViewById<TextView>(R.id.leaderboard_item_user).text = rankedUser.name

            card.findViewById<TextView>(R.id.leaderboard_item_score).text =
                rankedUser.points.toString()

            return card
        }
    }
}