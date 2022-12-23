package providers.manganese;

import providers.manganese.exceptions.ChapterNotFoundException;
import providers.manganese.exceptions.MangaNotFoundException;
import providers.manganese.exceptions.PageNotFoundException;

import java.util.*;

/**
 * Mock implementation of the {@link ManganeseApi} used for local testing. It
 * adds delays to simulate asynchronous actions and provides a synchronous api to facilitate testing.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public class MockManganeseApi implements ManganeseApi {

    @Override
    public List<Manga> searchManga(String title) {
        return mangasDb.stream().filter(m -> stringMatches(m.title(), title)).toList();
    }

    @Override
    public List<MangaChapter> fetchChapters(String mangaId) throws MangaNotFoundException {
        var chaptersId = mangaIdToChaptersId.getOrDefault(mangaId, null);
        if (chaptersId == null) {
            throw new MangaNotFoundException(mangaId);
        }
        return chaptersId.stream().map(chaptersDb::get).toList();
    }

    @Override
    public String fetchPage(String chapterId, int pageNumber) throws ChapterNotFoundException, PageNotFoundException {
        var chapter = chaptersDb.getOrDefault(chapterId, null);
        if (chapter == null) {
            throw new ChapterNotFoundException(chapterId);
        }
        if (!(1 <= pageNumber && pageNumber <= chapter.numberOfPages())) {
            throw new PageNotFoundException(pageNumber, chapterId);
        }

        // Just for testing
        return chapterId + pageNumber;
    }

    /**
     * Return true if a string resemble a query
     */
    private boolean stringMatches(String s, String query) {
        return s.toLowerCase().contains(query.toLowerCase());
    }


    /**
     * MOCK DATA, fetched from <a href="https://mangadex.org/">MangaDex</a>.
     * The attributes are public to make it easier to access from tests.
     */
    public final List<Manga> mangasDb = new ArrayList<>(Arrays.asList(
            new Manga("801513ba-a712-498c-8f57-cae55b38cc92",
                    "Berserk",
                    """
                    Guts, known as the Black Swordsman, seeks sanctuary from the demonic forces attracted to him and
                    his woman because of a demonic mark on their necks, and also vengeance against the man who branded
                    him as an unholy sacrifice. Aided only by his titanic strength gained from a harsh childhood lived
                    with mercenaries, a gigantic sword, and an iron prosthetic left hand, Guts must struggle against
                    his bleak destiny, all the while fighting with a rage that might strip him of his humanity.
                    """),
            new Manga("7e435a27-b4fc-417d-94de-30cea79ab6f4",
                    "Ping Pong",
                    """
                    (from ebookjapan):
                                    
                    The sport of table tennis by Taiyo Matsumoto! High school students and childhood friends Tsukimoto (Smile) and Hoshino (Peco), in the midst of their adolescence, run through the cold and cool world of "sports"!
                    Since their elementary school days when they played table tennis at Tamura in front of the station, Hoshino, who is a genius, always does whatever he wants including skipping club activities.
                    One day, again, he skipped club activities. Tsukimoto is ordered by his senpai to "bring Hoshino to the club"...
                    """),
            new Manga("dd8a907a-3850-4f95-ba03-ba201a8399e3",
                    "Fullmetal Alchemist",
                    """
                    In an alchemical ritual gone wrong, Edward Elric lost his arm and his leg, and his brother Alphonse became nothing but a soul in a suit of armor. Equipped with mechanical "auto-mail" limbs, Edward becomes a state alchemist, seeking the one thing that can restore his brother and himself… the legendary Philosopher's Stone
                                                                        
                    ---
                    - **Won the 49th Shogakukan Manga Award for Shonen**
                    - **Won the 15th Osamu Tezuka Cultural Award New Artist Prize**
                    - **Won the Seiun Award for Best Comic in 2011**""")));


    public final Map<String, List<String>> mangaIdToChaptersId = new HashMap<>() {{
        put("801513ba-a712-498c-8f57-cae55b38cc92", List.of("6310f6a1-17ee-4890-b837-2ec1b372905b", "da63389a-3d60-4634-8652-47a52e35eacc", "0ef160be-d552-471e-a841-a027b3410430", "996b6b09-692f-4e85-ace3-1619772be2c7", "0674fdf5-d93e-412d-8c59-e46daa432d77", "3604efc8-d1ef-4428-b657-678a60b1fcc9", "0a2a4b71-cdc3-47a5-be49-bfd09d666968", "e74b48d4-e471-4380-8e51-8fc6fb1448f9", "6eeb7e64-583e-4ca9-9e0c-4d6becad94e9", "6551e86a-e170-42b9-99a2-7559d9a9cb7f"));
        put("7e435a27-b4fc-417d-94de-30cea79ab6f4", List.of("86f8175f-b809-4469-9d9c-b1bb57a23ca5", "9d8851e9-00a3-4197-82e6-80448fa2f5b2", "2e7ff7b5-870c-4da4-821c-a659e2e8aa26", "44489923-081c-4447-b68a-414a9b5c30ab", "19d25715-6c56-4c6d-a131-5d8dc86da711", "1e3ac905-38aa-466c-a538-ba4c0c6d0db7", "a545d0bc-2abc-42fb-968a-41f78bb86f9a", "6967d732-54e3-47cf-9571-a6840c9926e4", "f18409a8-81aa-46b4-8b95-7af09c5f6f1b", "3286a2ab-2397-4b78-8fd1-04b2d4cda260"));
        put("dd8a907a-3850-4f95-ba03-ba201a8399e3", List.of("0a429639-4c47-481a-a12a-11860634e6de", "9c113f37-e7b7-430c-bbd0-63ea6977195b", "3376fe78-3b56-40ff-a689-490064a2c8b2", "1bba6d55-b7ba-4bea-9350-e023e244f378", "8861ab5d-1d29-4763-8f95-f3468c0c8d64", "46b2a96b-93a8-4c29-a7f7-376305776d5d", "2b724ab1-b457-4d06-aa3e-dee3c21e3ed6", "70803914-a2a9-47e1-a314-98240c25a96f", "bddeae3b-b889-4790-b1f0-a55860dacb39", "21ff5cd7-44df-4ea4-85a7-53f875832635"));

    }};

    public final Map<String, MangaChapter> chaptersDb = new HashMap<>() {{
        put("6310f6a1-17ee-4890-b837-2ec1b372905b", new MangaChapter("6310f6a1-17ee-4890-b837-2ec1b372905b", "801513ba-a712-498c-8f57-cae55b38cc92", "The Black Swordsman", 94));
        put("da63389a-3d60-4634-8652-47a52e35eacc", new MangaChapter("da63389a-3d60-4634-8652-47a52e35eacc", "801513ba-a712-498c-8f57-cae55b38cc92", "The Brand", 69));
        put("0ef160be-d552-471e-a841-a027b3410430", new MangaChapter("0ef160be-d552-471e-a841-a027b3410430", "801513ba-a712-498c-8f57-cae55b38cc92", "The Guardians of Desire (1)", 58));
        put("996b6b09-692f-4e85-ace3-1619772be2c7", new MangaChapter("996b6b09-692f-4e85-ace3-1619772be2c7", "801513ba-a712-498c-8f57-cae55b38cc92", "The Guardians of Desire (2) (LQ)", 118));
        put("0674fdf5-d93e-412d-8c59-e46daa432d77", new MangaChapter("0674fdf5-d93e-412d-8c59-e46daa432d77", "801513ba-a712-498c-8f57-cae55b38cc92", "The Guardians of Desire (3) (LQ)", 107));
        put("3604efc8-d1ef-4428-b657-678a60b1fcc9", new MangaChapter("3604efc8-d1ef-4428-b657-678a60b1fcc9", "801513ba-a712-498c-8f57-cae55b38cc92", "The Guardians of Desire (4) (LQ)", 58));
        put("0a2a4b71-cdc3-47a5-be49-bfd09d666968", new MangaChapter("0a2a4b71-cdc3-47a5-be49-bfd09d666968", "801513ba-a712-498c-8f57-cae55b38cc92", "The Guardians of Desire (5) (LQ)", 53));
        put("e74b48d4-e471-4380-8e51-8fc6fb1448f9", new MangaChapter("e74b48d4-e471-4380-8e51-8fc6fb1448f9", "801513ba-a712-498c-8f57-cae55b38cc92", "The Guardians of Desire (6) (LQ)", 61));
        put("6eeb7e64-583e-4ca9-9e0c-4d6becad94e9", new MangaChapter("6eeb7e64-583e-4ca9-9e0c-4d6becad94e9", "801513ba-a712-498c-8f57-cae55b38cc92", "The Golden Age (1) (LQ)", 47));
        put("6551e86a-e170-42b9-99a2-7559d9a9cb7f", new MangaChapter("6551e86a-e170-42b9-99a2-7559d9a9cb7f", "801513ba-a712-498c-8f57-cae55b38cc92", "The Golden Age (2)", 54));
        put("86f8175f-b809-4469-9d9c-b1bb57a23ca5", new MangaChapter("86f8175f-b809-4469-9d9c-b1bb57a23ca5", "7e435a27-b4fc-417d-94de-30cea79ab6f4", "Prologue", 12));
        put("9d8851e9-00a3-4197-82e6-80448fa2f5b2", new MangaChapter("9d8851e9-00a3-4197-82e6-80448fa2f5b2", "7e435a27-b4fc-417d-94de-30cea79ab6f4", "Smile", 33));
        put("2e7ff7b5-870c-4da4-821c-a659e2e8aa26", new MangaChapter("2e7ff7b5-870c-4da4-821c-a659e2e8aa26", "7e435a27-b4fc-417d-94de-30cea79ab6f4", "Peko", 20));
        put("44489923-081c-4447-b68a-414a9b5c30ab", new MangaChapter("44489923-081c-4447-b68a-414a9b5c30ab", "7e435a27-b4fc-417d-94de-30cea79ab6f4", "The Wind's Noise is Bothering Us", 18));
        put("19d25715-6c56-4c6d-a131-5d8dc86da711", new MangaChapter("19d25715-6c56-4c6d-a131-5d8dc86da711", "7e435a27-b4fc-417d-94de-30cea79ab6f4", "Wenga Kon", 18));
        put("1e3ac905-38aa-466c-a538-ba4c0c6d0db7", new MangaChapter("1e3ac905-38aa-466c-a538-ba4c0c6d0db7", "7e435a27-b4fc-417d-94de-30cea79ab6f4", "The Hero", 18));
        put("a545d0bc-2abc-42fb-968a-41f78bb86f9a", new MangaChapter("a545d0bc-2abc-42fb-968a-41f78bb86f9a", "7e435a27-b4fc-417d-94de-30cea79ab6f4", "The Old Man and the Young Boy", 18));
        put("6967d732-54e3-47cf-9571-a6840c9926e4", new MangaChapter("6967d732-54e3-47cf-9571-a6840c9926e4", "7e435a27-b4fc-417d-94de-30cea79ab6f4", "The Dragon", 22));
        put("f18409a8-81aa-46b4-8b95-7af09c5f6f1b", new MangaChapter("f18409a8-81aa-46b4-8b95-7af09c5f6f1b", "7e435a27-b4fc-417d-94de-30cea79ab6f4", "Youth", 18));
        put("3286a2ab-2397-4b78-8fd1-04b2d4cda260", new MangaChapter("3286a2ab-2397-4b78-8fd1-04b2d4cda260", "7e435a27-b4fc-417d-94de-30cea79ab6f4", "Buttefly Joe", 18));
        put("0a429639-4c47-481a-a12a-11860634e6de", new MangaChapter("0a429639-4c47-481a-a12a-11860634e6de", "dd8a907a-3850-4f95-ba03-ba201a8399e3", "The Two Alchemists", 52));
        put("9c113f37-e7b7-430c-bbd0-63ea6977195b", new MangaChapter("9c113f37-e7b7-430c-bbd0-63ea6977195b", "dd8a907a-3850-4f95-ba03-ba201a8399e3", "The Price Of A Life", 39));
        put("3376fe78-3b56-40ff-a689-490064a2c8b2", new MangaChapter("3376fe78-3b56-40ff-a689-490064a2c8b2", "dd8a907a-3850-4f95-ba03-ba201a8399e3", "The Coal Mine Town", 42));
        put("1bba6d55-b7ba-4bea-9350-e023e244f378", new MangaChapter("1bba6d55-b7ba-4bea-9350-e023e244f378", "dd8a907a-3850-4f95-ba03-ba201a8399e3", "Battle On The Train", 45));
        put("8861ab5d-1d29-4763-8f95-f3468c0c8d64", new MangaChapter("8861ab5d-1d29-4763-8f95-f3468c0c8d64", "dd8a907a-3850-4f95-ba03-ba201a8399e3", "An Alchemist's Anguish", 47));
        put("46b2a96b-93a8-4c29-a7f7-376305776d5d", new MangaChapter("46b2a96b-93a8-4c29-a7f7-376305776d5d", "dd8a907a-3850-4f95-ba03-ba201a8399e3", "The Right Hand Of Destruction", 46));
        put("2b724ab1-b457-4d06-aa3e-dee3c21e3ed6", new MangaChapter("2b724ab1-b457-4d06-aa3e-dee3c21e3ed6", "dd8a907a-3850-4f95-ba03-ba201a8399e3", "After The Rain", 39));
        put("70803914-a2a9-47e1-a314-98240c25a96f", new MangaChapter("70803914-a2a9-47e1-a314-98240c25a96f", "dd8a907a-3850-4f95-ba03-ba201a8399e3", "A Hopeful Road", 51));
        put("bddeae3b-b889-4790-b1f0-a55860dacb39", new MangaChapter("bddeae3b-b889-4790-b1f0-a55860dacb39", "dd8a907a-3850-4f95-ba03-ba201a8399e3", "The House Where Their Family Is Waiting", 45));
        put("21ff5cd7-44df-4ea4-85a7-53f875832635", new MangaChapter("21ff5cd7-44df-4ea4-85a7-53f875832635", "dd8a907a-3850-4f95-ba03-ba201a8399e3", "The Philosopher's Stone", 35));

    }};
}
