package ch.epfl.sweng.ctf.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ch.epfl.sweng.ctf.R
import ch.epfl.sweng.ctf.models.Challenge
import ch.epfl.sweng.ctf.repositories.ChallengesRepository
import org.koin.android.ext.android.inject

/**
 * Challenges fragment
 * Displays list of challenges
 *
 * @author Alexandre Chau
 */
class ChallengesListFragment : Fragment() {
    private val challengesRepository: ChallengesRepository by inject()
    private val challengesListView: ListView? by lazy { view?.findViewById(R.id.list_challenges) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        updateList()
    }

    override fun onResume() {
        super.onResume()
        updateList()
    }

    private fun updateList() {
        val challengesListAdapter = context?.let { ctx ->
            ChallengesListViewAdapter(
                ctx,
                R.id.list_challenges,
                challengesRepository.getAllChallenges()
            )
        }
        challengesListView?.adapter = challengesListAdapter
        challengesListAdapter?.notifyDataSetChanged()
    }

    /**
     * Collection adapter between list of challenge models and ListView display
     * @param ctx UI context in which the ListView lives
     * @param res ListView of where the challenges will be displayed
     * @param challenges List<Challenge> to display
     */
    private inner class ChallengesListViewAdapter(
        private val ctx: Context,
        res: Int,
        private var challenges: List<Challenge>
    ) : ArrayAdapter<Challenge>(ctx, res, challenges) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val card =
                convertView ?: layoutInflater.inflate(
                    R.layout.list_item_challenge,
                    challengesListView,
                    false
                )
            val challenge = challenges[position]

            card.findViewById<ImageView>(R.id.challenge_icon)
                .setImageDrawable(ContextCompat.getDrawable(ctx, challenge.imageRes))

            card.findViewById<TextView>(R.id.challenge_title).text = challenge.name

            card.findViewById<TextView>(R.id.challenge_points).text =
                ctx.getString(R.string.challenge_n_points, challenge.points)

            val imageView = card.findViewById<ImageView>(R.id.challenge_status)
            val drawable = when (challenge.status) {
                Challenge.Status.PENDING -> null
                Challenge.Status.SOLVED -> ContextCompat.getDrawable(
                    ctx,
                    R.drawable.icon_round_check
                )
            }
            imageView.setImageDrawable(drawable)

            card.setOnClickListener {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    challengesRepository.getIdOfChallenge(challenge)?.let { id ->
                        add(R.id.main_fragment, ChallengeFragment.newInstance(id))
                        addToBackStack(null)
                    }
                }
            }

            return card
        }
    }
}