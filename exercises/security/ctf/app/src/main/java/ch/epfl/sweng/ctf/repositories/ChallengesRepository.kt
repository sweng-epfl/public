package ch.epfl.sweng.ctf.repositories

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteException
import android.os.Environment
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import ch.epfl.sweng.ctf.Config
import ch.epfl.sweng.ctf.R
import ch.epfl.sweng.ctf.models.Challenge
import ch.epfl.sweng.ctf.models.Fruit
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Interface for a Challenge repository
 * Contains all available challenges data models
 *
 * @author Alexandre Chau
 */
interface ChallengesRepository {
    /**
     * Provides all challenges
     */
    fun getAllChallenges(): List<Challenge>

    /**
     * Provides a single challenge given its ID
     */
    fun getChallengeById(id: Int): Challenge

    /**
     * Provides the ID for a given challenge
     */
    fun getIdOfChallenge(challenge: Challenge): Int?

    /**
     * Notify data set changes
     */
    fun notifyDatasetChanged()

    /**
     * Compute the current score of the user
     */
    fun getCurrentScore(): Int
}

/**
 * Implementation for a Challenge repository
 * Instantiates models in memory and combines them with relevant persisted state
 *
 * @param flagsRepository Flags repository containing verification logic
 *
 * @author Alexandre Chau
 */
class ChallengesRepositoryImpl(
    private val flagsRepository: FlagsRepository,
    private val fruitsRepository: FruitsRepository,
) :
    ChallengesRepository {

    /**
     * Provides the url to the public repo of this application (without this file) up to
     * the ch.epfl.sweng.ctf package
     *
     * @example https://github.com/sweng-epfl/public/tree/master/exercises/security/ctf/app/src/main/java/ch/epfl/sweng/ctf/
     */
    private fun getPublicRepoUrlUpToPackage(): String =
        getPublicRepoUrlUpToMain() + "java/ch/epfl/sweng/ctf/"

    private fun getPublicRepoUrlUpToMain(): String =
        "https://github.com/sweng-epfl/public/tree/master/exercises/security/ctf/app/src/main/"

    /**
     * Bridge to the low-level C++ native-lib method (see app/src/main/cpp/native-lib.cpp)
     *
     * @param userInput user input provided from UI
     * @param flag provided by [flagsRepository]
     */
    external fun nativeString(userInput: String, flag: String): String

    /**
     * The raw data of the challenges
     */
    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    private val challengesData: List<Challenge> = listOf(
        Challenge(
            name = "Intro",
            imageRes = R.drawable.icon_round_school,
            points = 1,
            description = """
                This challenge will let you get familiar with the CTF infrastructure within the application. Your goal is to attack the app by inspecting its (hidden) features, reading its source code and fiddling around with the Android environment. Each vulnerability illustrates a common mobile development mistake, which may allow adversaries to trigger bugs, unintended behavior, unauthorized accesses and secret leaks in real apps. Most challenges were inspired by real system weaknesses and adapted to a simpler scenario.
                
                Solving a challenge will provide you with a string token flag (the stolen secret), which you will need to submit in the box below each challenge description. Flags may be found outside of the application itself or may need to be leaked into the app. Flags have the following format: ${
                flagsRepository.getFlagTemplate().replace("%s", "secret_flag_value")
            }
                
                You can play this CTF on several difficulties: if you already are familiar with Android security, you can try to solve the challenges without looking at the hints or the source code! Otherwise, we provide hints in each description which will progressively spoil more of the solution, such as vulnerability type, location of the bug in code, … Make sure to pay attention to the challenge title, image and description, as they will also contain hints.
            """.trimIndent(),
            hints = listOf(
                Challenge.Hint(
                    "Example",
                    "The flag is ${flagsRepository.getFlag("Intro")}\nYou can select it with a long click"
                ),
            ),
        ),

        Challenge(
            name = "\"This will be removed in prod\"",
            imageRes = R.drawable.icon_round_gear,
            points = 5,
            description = """
                Don't look too far, sometimes all the tools you need to hack something are already given to you.
            """.trimIndent(),
            hints = listOf(
                Challenge.Hint(
                    "Cause",
                    "Sometimes, developers get lazy and assume that work in progress will be removed later... except when it doesn't."
                ),
                Challenge.Hint(
                    "Vulnerability type",
                    "OWASP M10: Extraneous Functionality (see https://owasp.org/www-project-mobile-top-10/2016-risks/m10-extraneous-functionality )"
                ),
                Challenge.Hint(
                    "LOC",
                    getPublicRepoUrlUpToPackage() + "Config.kt#L26"
                ),
            ),
            uiHooks = Challenge.FragmentHooks(
                onCreateView = { view, _, inflater, _ ->
                    inflater.inflate(R.layout.challenge_this_will_be_removed_in_prod, view)
                },
                onResume = { view, _ ->
                    val debugTextView =
                        view.findViewById<TextView>(R.id.challenge_this_will_be_removed_in_prod_debug_text)
                    if (Config.debug) {
                        debugTextView.text =
                            "Flag: ${flagsRepository.getFlag("\"This will be removed in prod\"")}"
                    } else {
                        debugTextView.text = "Suspicious text that should definitely not be in prod"
                    }
                }
            )
        ),

        Challenge(
            name = "Hidden in plain s(d)ight",
            imageRes = R.drawable.icon_round_sdcard,
            points = 10,
            description = """
                There are many places to store secrets, although not all of them are good places to hide secrets.
            """.trimIndent(),
            hints = listOf(
                Challenge.Hint(
                    "Vulnerability type",
                    "OWASP M2: Insecure Data Storage (see https://owasp.org/www-project-mobile-top-10/2016-risks/m2-insecure-data-storage )"
                ),
                Challenge.Hint(
                    "LOC",
                    getPublicRepoUrlUpToPackage() + "repositories/ChallengesRepository.kt#L188-L198"
                ),
            ),
            uiHooks = Challenge.FragmentHooks(
                onCreateView = { view, ctx, _, _ ->
                    // helper to show unexpected error on UI
                    fun showError() {
                        Snackbar.make(
                            view,
                            "Error: external storage unavailable (could not write flag)",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    // Checks if a volume containing external storage is available for read and write.
                    if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) showError()
                    else {
                        val primaryExternalStorage =
                            ContextCompat.getExternalFilesDirs(ctx, null).getOrNull(0)
                        if (primaryExternalStorage == null) showError()
                        else {
                            val filename = "super_safe_storage_amiright.txt"
                            val file = File(primaryExternalStorage, filename)
                            file.writeText(
                                flagsRepository.getFlag("Hidden in plain s(d)ight"),
                                Charset.defaultCharset()
                            )
                        }
                    }
                }
            ),
        ),

        Challenge(
            name = "Early bird",
            imageRes = R.drawable.icon_round_clock,
            points = 10,
            description = """
                What can you trust?
            """.trimIndent(),
            hints = listOf(
                Challenge.Hint("Vulnerability type", "Lack of sanitization of user input"),
                Challenge.Hint(
                    "LOC",
                    getPublicRepoUrlUpToPackage() + "repositories/ChallengesRepository.kt#L225"
                ),
            ),
            uiHooks = Challenge.FragmentHooks(
                onCreateView = { view, _, inflater, _ ->
                    inflater.inflate(R.layout.challenge_early_bird, view)
                },
                onResume = { view, _ ->
                    val now = Calendar.getInstance().time
                    val until = Calendar.getInstance()
                    until.set(2030, 1, 1)
                    val release = until.time

                    view.findViewById<TextView>(R.id.challenge_early_bird_text).text =
                        if (now < release)
                            "${TimeUnit.MILLISECONDS.toDays(release.time - now.time)} days"
                        else flagsRepository.getFlag("Early bird")
                },
            )
        ),

        Challenge(
            name = "Fruity injection",
            imageRes = R.drawable.icon_table_rows,
            points = 20,
            description = """
                I used a library, I should be safe right?
            """.trimIndent(),
            hints = listOf(
                Challenge.Hint(
                    "Vulnerability type",
                    "Injection (see https://owasp.org/www-community/Injection_Flaws )"
                ),
                Challenge.Hint(
                    "LOC",
                    getPublicRepoUrlUpToPackage() + "repositories/FruitsRepository.kt#L100",
                )
            ),
            uiHooks = Challenge.FragmentHooks(
                onCreateView = { view, ctx, inflater, lifecycleScope ->
                    val challengeView = inflater.inflate(R.layout.challenge_fruity_injection, view)

                    val searchInput =
                        challengeView.findViewById<EditText>(R.id.challenge_fruity_injection_input)
                    val fruitsListView =
                        challengeView.findViewById<ViewGroup>(R.id.challenge_fruity_injection_fruits_list)

                    suspend fun updateFruitsList(keyword: String?) {
                        try {
                            val fruits = fruitsRepository.searchFruits(keyword)
                            lifecycleScope.launch {
                                fruitsListView.removeAllViews()
                                fruits.forEach { fruit ->
                                    val fruitView = inflater.inflate(
                                        R.layout.list_item_fruit,
                                        fruitsListView,
                                        false
                                    )
                                    (fruitView as? TextView)?.text = fruit.name
                                    fruitsListView.addView(fruitView)
                                }
                            }
                        } catch (e: SQLiteException) {
                            /* no-op */
                        }
                    }

                    searchInput.addTextChangedListener { text ->
                        GlobalScope.launch {
                            updateFruitsList(text?.toString())
                        }
                    }

                    GlobalScope.launch {
                        val count = fruitsRepository.countFruits()
                        if (count == 0) {
                            fruitsRepository.addFruit(Fruit("banana"))
                            fruitsRepository.addFruit(Fruit("apple"))
                            fruitsRepository.addFruit(Fruit("kiwi"))
                            fruitsRepository.addFruit(Fruit("mango"))
                            fruitsRepository.addFruit(Fruit("pineapple"))
                            fruitsRepository.addFruit(Fruit(flagsRepository.getFlag("Fruity injection")))
                        }
                        updateFruitsList(null)
                    }
                }
            ),
        ),

        Challenge(
            name = "Crossy web",
            imageRes = R.drawable.icon_round_web,
            points = 20,
            description = """
                Thanks to WebView, we can remind you of the SwEng website:
            """.trimIndent(),
            hints = listOf(
                Challenge.Hint(
                    "Vulnerability type",
                    "XSS (see https://owasp.org/www-community/attacks/xss/ )"
                ),
                Challenge.Hint(
                    "LOC",
                    "${getPublicRepoUrlUpToMain() + "assets/sweng.epfl.ch.html"}\n\n${getPublicRepoUrlUpToPackage() + "repositories/ChallengesRepository.kt#L330"}"
                ),
            ),
            uiHooks = Challenge.FragmentHooks(
                onCreateView = { view, ctx, inflater, _ ->
                    inflater.inflate(R.layout.challenge_crossy_web, view)
                    val webView = view.findViewById<WebView>(R.id.challenge_crossy_web_webview)
                    val webContent =
                        ctx.assets.open("sweng.epfl.ch.html").bufferedReader()
                            .use { it.readText() } +
                                "\n<script>window.flag = \"${flagsRepository.getFlag("Crossy web")}\"</script>"
                    webView.settings.javaScriptEnabled = true
                    webView.webChromeClient = WebChromeClient()
                    webView.loadDataWithBaseURL(
                        "file:///android_asset/sweng.epfl.ch.html",
                        webContent,
                        "text/html",
                        "utf8",
                        null
                    )
                }
            ),
        ),

        Challenge(
            name = "The debugger is overrated",
            imageRes = R.drawable.icon_list,
            points = 10,
            description = """
                Surely printf and logs are enough... except when they end up leaking secrets.
            """.trimIndent(),
            hints = listOf(
                Challenge.Hint(
                    "Vulnerability type",
                    "OWASP M10: Extraneous Functionality (see https://owasp.org/www-project-mobile-top-10/2016-risks/m10-extraneous-functionality )"
                ),
                Challenge.Hint(
                    "LOC",
                    getPublicRepoUrlUpToPackage() + "repositories/ChallengesRepository.kt#L363"
                ),
            ),
            uiHooks = Challenge.FragmentHooks(
                onResume = { _, _ ->
                    Log.d(
                        "DEBUG_FLAG",
                        flagsRepository.getFlag("The debugger is overrated")
                    )
                }
            )
        ),

        Challenge(
            name = "On the wire",
            imageRes = R.drawable.icon_wifi,
            points = 15,
            description = """
                Big Candea is watching you
            """.trimIndent(),
            hints = listOf(
                Challenge.Hint(
                    "Vulnerability type",
                    "M3: Insecure Communication (see https://owasp.org/www-project-mobile-top-10/2016-risks/m3-insecure-communication )"
                ),
                Challenge.Hint(
                    "Tool",
                    "https://developer.android.com/studio/profile/network-profiler"
                ),
            ),
            uiHooks = Challenge.FragmentHooks(
                onResume = { view, ctx ->
                    val queue = Volley.newRequestQueue(ctx)
                    val url = "http://sweng.epfl.ch?flag=" + flagsRepository.getFlag("On the wire")
                    val callback = { _: Any ->
                        Snackbar.make(view, "Network request sent", Snackbar.LENGTH_LONG).show()
                    }
                    val request = StringRequest(Request.Method.GET, url, callback, callback)
                    queue.add(request)
                }
            )
        ),

        Challenge(
            name = "Java++",
            imageRes = R.drawable.icon_memory,
            points = 30,
            description = """
                Closer to the metal and to your secrets
            """.trimIndent(),
            hints = listOf(
                Challenge.Hint(
                    "Vulnerability type",
                    "M7: Poor Code Quality (see https://owasp.org/www-project-mobile-top-10/2016-risks/m7-client-code-quality )"
                ),
                Challenge.Hint(
                    "LOC",
                    getPublicRepoUrlUpToMain() + "cpp/native-lib.cpp"
                ),
            ),
            uiHooks = Challenge.FragmentHooks(
                onCreateView = { view, _, inflater, _ ->
                    inflater.inflate(R.layout.challenge_javapp, view)
                    val input = view.findViewById<EditText>(R.id.challenge_javapp_input)
                    val reply = view.findViewById<TextView>(R.id.challenge_javapp_reply)
                    input.addTextChangedListener { text ->
                        reply.text =
                            nativeString(text.toString(), flagsRepository.getFlag("Java++"))
                    }
                },
            )
        ),

        Challenge(
            name = "gnireenignE",
            imageRes = R.drawable.icon_search,
            points = 20,
            description = """
                Oops, it looks like we did not hide a flag properly. Can you find it?
            """.trimIndent(),
            hints = listOf(
                Challenge.Hint(
                    "Vulnerability type",
                    "Reverse engineering (see https://owasp.org/www-project-mobile-top-10/2016-risks/m9-reverse-engineering )"
                ),
                Challenge.Hint("Tool", "https://ibotpeaches.github.io/Apktool/"),
            ),
        ),
    )

    /**
     * Data of the challenges, dynamically updated with persisted state
     */
    private var challenges = loadChallengesWithPersistedState()

    override fun getAllChallenges(): List<Challenge> {
        return challenges
    }

    override fun getChallengeById(id: Int): Challenge {
        return challenges[id]
    }

    override fun getIdOfChallenge(challenge: Challenge): Int? {
        val idx = challenges.indexOf(challenge)
        return if (idx == -1) null else idx
    }

    override fun notifyDatasetChanged() {
        this.challenges = loadChallengesWithPersistedState()
    }

    override fun getCurrentScore(): Int {
        return challenges.filter { c -> c.status == Challenge.Status.SOLVED }
            .fold(0, { acc, c -> acc + c.points })
    }

    // Reconcile challenge data with challenge state from flagsRepository
    private fun loadChallengesWithPersistedState(): List<Challenge> =
        flagsRepository.combineWithPersistedState(challengesData)
}