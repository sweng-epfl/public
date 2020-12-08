package ch.epfl.sweng.ctf.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ch.epfl.sweng.ctf.R
import ch.epfl.sweng.ctf.let
import ch.epfl.sweng.ctf.models.Challenge
import ch.epfl.sweng.ctf.repositories.ChallengesRepository
import ch.epfl.sweng.ctf.repositories.FlagsRepository
import org.koin.android.ext.android.inject


/**
 * Fragment to display any challenge
 * Provides display to read info and solve a challenge
 *
 * @author Alexandre Chau
 */
class ChallengeFragment : Fragment() {

    private val challengesRepository: ChallengesRepository by inject()
    private val flagsRepository: FlagsRepository by inject()
    private var challengeId: Int? = null
    private var challenge: Challenge? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            challengeId = bundle.getInt(EXTRA_TAG_CHALLENGE_ID)
            challenge = challengeId?.let { id -> challengesRepository.getChallengeById(id) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_challenge, container, false)

        let(challenge, context) { challenge, ctx ->
            view.findViewById<TextView>(R.id.challenge_title).text = challenge.name
            view.findViewById<TextView>(R.id.challenge_subtitle).text =
                getString(R.string.challenge_n_points, challenge.points)
            view.findViewById<ImageView>(R.id.challenge_icon)
                .setImageDrawable(ContextCompat.getDrawable(ctx, challenge.imageRes))
            view.findViewById<TextView>(R.id.challenge_desc).text = challenge.description

            displayHints(challenge, view, ctx)
            displaySubmission(challenge, view)

            view.findViewById<Button>(R.id.flag_submit_button).setOnClickListener {
                checkFlag(challenge, view)
            }

            // execute challenge onCreateView hook if it exists
            challenge.uiHooks?.onCreateView?.let { onCreateViewHook ->
                val innerView = view.findViewById<ViewGroup>(R.id.challenge_inner_layout)
                onCreateViewHook(innerView, ctx, inflater, lifecycleScope)
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStop() {
        super.onStop()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onResume() {
        super.onResume()
        // reload data as it may have been wiped out
        challenge = challengeId?.let { id -> challengesRepository.getChallengeById(id) }
        let(challenge, view, context) { challenge, view, ctx ->
            displaySubmission(challenge, view)

            // execute challenge onResume hook if it exists
            challenge.uiHooks?.onResume?.let { onResumeHook ->
                val innerView = view.findViewById<View>(R.id.challenge_inner_layout)
                onResumeHook(innerView, ctx)
            }
        }
    }

    // Helper to display the list of hints
    private fun displayHints(challenge: Challenge, view: View, ctx: Context) {
        val challengeLayout = view.findViewById<LinearLayout>(R.id.challenge_layout)

        // hints list must be reversed because we insert at fixed position from top of layout
        challenge.hints.reversed().forEach { hint ->
            val hintView = layoutInflater.inflate(
                R.layout.cardview_expandable_hint,
                challengeLayout,
                false
            )
            hintView.findViewById<TextView>(R.id.hint_title).text =
                getString(R.string.hint_title, hint.category)
            val hintTextView = hintView.findViewById<TextView>(R.id.hint_text)
            hintTextView.text = hint.text

            val hintExpandArrow = hintView.findViewById<ImageView>(R.id.hint_expand_arrow)
            hintView.setOnClickListener {
                when (hintTextView.visibility) {
                    View.GONE -> {
                        hintExpandArrow.setImageDrawable(
                            ContextCompat.getDrawable(
                                ctx,
                                R.drawable.icon_arrow_up
                            )
                        )
                        hintTextView.visibility = View.VISIBLE
                    }
                    View.VISIBLE -> {
                        hintExpandArrow.setImageDrawable(
                            ContextCompat.getDrawable(
                                ctx,
                                R.drawable.icon_arrow_down
                            )
                        )
                        hintTextView.visibility = View.GONE
                    }
                }
            }

            challengeLayout.addView(hintView, HINT_VIEWS_INSERTION_INDEX)
        }
    }

    // Helper to display the flag submission status
    private fun displaySubmission(challenge: Challenge, view: View) {
        val flagSubmitView = view.findViewById<View>(R.id.flag_submit)
        val flagSuccessView = view.findViewById<View>(R.id.flag_success)
        when (challenge.status) {
            Challenge.Status.PENDING -> {
                flagSubmitView.visibility = View.VISIBLE
                flagSuccessView.visibility = View.GONE
            }
            Challenge.Status.SOLVED -> {
                flagSubmitView.visibility = View.GONE
                flagSuccessView.visibility = View.VISIBLE
            }
        }
    }

    // Helper to check flags
    private fun checkFlag(challenge: Challenge, view: View) {
        val valueEditText = view.findViewById<EditText>(R.id.flag_submit_value)
        if (flagsRepository.checkFlag(challenge.name, valueEditText.text.toString())) {
            // reload challenge
            challengesRepository.notifyDatasetChanged()
            this.challenge = challengeId?.let { id -> challengesRepository.getChallengeById(id) }
            this.challenge?.let { it -> displaySubmission(it, view) }
        } else {
            valueEditText.error = getString(R.string.flag_incorrect)
        }
    }

    companion object {
        const val EXTRA_TAG_CHALLENGE_ID: String = "EXTRA_CHALLENGE_ID"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param challengeId ID of the challenge to display, as indexed by [ch.epfl.sweng.ctf.repositories.ChallengesRepositoryImpl]
         * @return A new instance of fragment ChallengeFragment
         */
        @JvmStatic
        fun newInstance(challengeId: Int) =
            ChallengeFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_TAG_CHALLENGE_ID, challengeId)
                }
            }

        // Index at which hints should be inserted into the challenge layout
        private const val HINT_VIEWS_INSERTION_INDEX = 1
    }
}