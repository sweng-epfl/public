package ch.epfl.sweng.ctf.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import ch.epfl.sweng.ctf.models.Challenge.FragmentHooks
import ch.epfl.sweng.ctf.models.Challenge.Hint

/**
 * A model representing a CTF challenge
 *
 * @author Alexandre Chau
 *
 * @param name The name of the challenge
 * @param imageRes The drawable identifier for the image of the challenge (from res/drawable/)
 * @param points The number of points associated to this challenge
 * @param status The current status of the challenge
 * @param description The textual description of the challenge
 * @param hints A list of [Hint] to progressively spoil the solution of the challenge
 * @param uiHooks A [FragmentHooks] that registers callbacks that are executed by the UI of [ch.epfl.sweng.ctf.fragments.ChallengeFragment]
 */
data class Challenge(
    val name: String,
    val imageRes: Int,
    val points: Int,
    val status: Status = Status.PENDING,
    val description: String,
    val hints: List<Hint>,
    val uiHooks: FragmentHooks? = null,
) {
    /**
     * The challenge is either solved or pending
     */
    enum class Status {
        PENDING,
        SOLVED,
    }

    /**
     * Holder class modeling a hint
     * @param category Kind of hint (for instance "Vulnerability type" or "LOC")
     * @param text Actual text of the hint, that should help solve the challenge
     */
    data class Hint(val category: String, val text: String)

    /**
     * Factory to create an immutable copy with an updated status
     */
    fun withStatus(newStatus: Status): Challenge {
        return Challenge(
            this.name,
            this.imageRes,
            this.points,
            newStatus,
            this.description,
            this.hints,
            this.uiHooks,
        )
    }

    /**
     * Callbacks executed by the UI at [ch.epfl.sweng.ctf.fragments.ChallengeFragment]
     * Names match the respective method hooks in [androidx.fragment.app.Fragment]
     *
     * @param onCreateView Nullable callback invoked by [ch.epfl.sweng.ctf.fragments.ChallengeFragment.onCreateView] at the creation of the Challenge view
     * @param onResume Nullable callback invoked by [ch.epfl.sweng.ctf.fragments.ChallengeFragment.onResume] every time the Challenge view is brought up to the user
     */
    data class FragmentHooks(
        val onCreateView: ((view: ViewGroup, ctx: Context, inflater: LayoutInflater, lifecycleScope: LifecycleCoroutineScope) -> Unit)? = null,
        val onResume: ((view: View, ctx: Context) -> Unit)? = null,
    )
}