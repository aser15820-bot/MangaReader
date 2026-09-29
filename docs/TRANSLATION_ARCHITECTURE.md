# Translation Architecture — MangaReader

## 1 — Current architecture (summary)
- Android app written in Kotlin using Jetpack Compose for UI.
- Image loading: Coil (io.coil-kt:coil-compose).
- Main reader UI implemented as a Compose screen: app/src/main/java/com/aser15820/mangareader/ui/screens/ReaderScreen.kt.
- No existing OCR, translation, Room, WorkManager, or networking libraries detected in current app module.
- Entry points: MainActivity -> MangaReaderApp -> HomeScreen -> MangaDetailScreen -> ReaderScreen.

## 2 — Relevant existing components
- Compose-based ReaderScreen (app/src/main/java/com/aser15820/mangareader/ui/screens/ReaderScreen.kt) — integration point for overlay/controls.
- Image loader: Coil (used via AsyncImage in screens).
- App build config: app/build.gradle.kts (Compose enabled, Kotlin/Java 17).
- Manifest: app/src/main/AndroidManifest.xml (declares INTERNET permission).

## 3 — Proposed translation architecture (high-level)
We will add a modular pipeline that processes each manga page image through replaceable stages:

Manga Page Image
  ↓ ImagePreprocessor (scaling, contrast, deskew) [interface]
  ↓ TextDetector (bounding boxes) [interface]
  ↓ MangaOcrEngine (recognition on regions; returns text + boxes + orientation) [interface]
  ↓ TextSegmenter (merge/segment OCR results into bubbles/units) [interface]
  ↓ TranslationEngine (provider-agnostic string translation) [interface]
  ↓ TranslationCache (persistent Room-backed cache) [interface]
  ↓ TranslationRenderer (renders translated text into an overlay Composable/View) [interface]
  ↓ Translated Manga Page (original image + overlay)

Characteristics:
- Each stage is an interface with one or more implementations.
- Default OCR provider: on-device ML Kit wrapper (recommended).
- Translation providers: pluggable (Local on-device model optional, Remote provider via Retrofit for cloud services).
- Persistent caches (OCR results and translations) stored in Room DB for offline availability.
- Chapter translation performed by WorkManager for background processing, progress, and cancellation.

## 4 — Integration points (where code will be modified)
Primary integration:
- ReaderScreen.kt — add Translate controls (button/menu) and show TranslationOverlayComposable on top of page image.
- MangaDetailScreen.kt — add "Translate Chapter" action in chapter controls (optional).
- HomeScreen / navigation (if needed) — wiring for settings screen.
- New Room DB accessed via a repository adapter within existing data/repository package.

## 5 — Files to be modified (exact list — tentative)
- app/build.gradle.kts — add dependencies (Room, WorkManager, ML Kit, Retrofit/OkHttp, coroutines, AndroidX Security, testing libs).
- app/src/main/AndroidManifest.xml — add required permissions (INTERNET already present; optionally POST_NOTIFICATIONS if needed for background progress).
- app/src/main/java/com/aser15820/mangareader/ui/screens/ReaderScreen.kt — integrate Translate UI controls & overlay Composable; hook into pipeline.
- app/src/main/java/com/aser15820/mangareader/ui/screens/MangaDetailScreen.kt — add chapter-translate entry (UI).
- app/src/main/java/com/aser15820/mangareader/ui/MangaApp.kt / MainActivity.kt — minor wiring if global DI or Service initialization is necessary.

## 6 — New files/packages to create
Under app/src/main/java/com/aser15820/mangareader/translation/:
- api/
  - TranslationEngine.kt (interface)
  - TranslationProvider.kt (interface)
  - TranslationResult.kt (data class)
- ocr/
  - MangaOcrEngine.kt (interface)
  - OcrResult.kt (data class)
  - MlKitOcrProvider.kt (implementation wrapper for ML Kit)
  - TesseractOcrProvider.kt (optional)
  - TextDetector.kt (interface)
- pipeline/
  - TranslationPipeline.kt (orchestrator that composes stages and exposes suspend/Flow APIs)
- cache/
  - TranslationCache.kt (interface)
  - entities/ (Room entities: OcrEntity, TranslationEntity)
  - dao/ (Room DAOs)
  - TranslationCacheRoomImpl.kt
- renderer/
  - TranslationOverlayComposable.kt (Compose overlay rendering translated bubbles)
  - TranslationRenderer.kt (interface and helpers for RTL/vertical handling)
- work/
  - ChapterTranslationWorker.kt (WorkManager worker for background chapter translation)
- ui/
  - TranslateControlsComposable.kt (buttons, language selection, toggles)
  - TranslationSettingsScreen.kt (settings integration)
- docs/ (OCR.md, TRANSLATION.md, DEVELOPMENT.md) — docs to be added.

## 7 — Dependencies required (recommended)
- Kotlin Coroutines (org.jetbrains.kotlinx:kotlinx-coroutines-android) — concurrency.
- Room (androidx.room:room-runtime + room-ktx + annotationProcessor) — persistent cache.
- WorkManager (androidx.work:work-runtime-ktx) — chapter background jobs.
- ML Kit Text Recognition (com.google.mlkit:text-recognition or text-recognition-japanese where applicable) — on-device OCR.
- ML Kit or Retrofit for translation:
  - Option A (on-device): ML Kit Translate (com.google.mlkit:translate) — check availability for target languages.
  - Option B (remote): Retrofit + OkHttp + provider adapter (OpenAI/Google Cloud/other) — provider-agnostic adapter required.
- AndroidX Security Crypto (androidx.security:security-crypto) or EncryptedSharedPreferences — secure API key storage.
- Coil (already present) — image loading.
- Optional: tess-two (Tesseract) if ML Kit insufficient for vertical Japanese, but adds native libs and size.
- Testing: JUnit, MockK (or Mockito), Espresso for UI tests.
Notes: Avoid adding heavy on-device translation models to APK — use model downloads or remote provider.

## 8 — Risks & compatibility concerns
- APK size & model downloads: on-device models can increase size; prefer downloadable models and warn user.
- Memory/OOM: OCR & bitmap processing must use scaled bitmaps and be limited in concurrency; release bitmaps promptly.
- Language support: on-device translation may not support all pairs (Japanese→Arabic may be limited); build provider-fallbacks.
- Vertical Japanese text: detection + correct OCR & rendering require careful orientation detection; ML Kit support varies.
- Arabic rendering: must ensure proper RTL shaping and line-breaking; Android native text shaping generally supports Arabic, but overlay/renderer must manage wrapping and font scaling.
- Security: never commit API keys; store them using AndroidX Security; do not log sensitive info.
- UX: chapter translation can be long-running; provide progress, cancellation, and not block UI.

## 9 — Implementation order (incremental)
1. Add domain interfaces (TranslationEngine, MangaOcrEngine, TranslationCache) and small, testable skeletons.
2. Implement OCR abstraction + ML Kit provider (on-device).
3. Implement TranslationEngine abstraction + a Remote provider adapter (skeleton, no API keys committed).
4. Add Room entities & DAO for caching OCR results and translations.
5. Implement TranslationPipeline (orchestrator) to run OCR→translate→cache for a page.
6. Integrate a TranslationOverlayComposable and TranslateControls into ReaderScreen for single-page translation (MVP).
7. Implement ChapterTranslationWorker using WorkManager for chapter translations with progress and cancellation.
8. Add settings screen items and secure storage for credentials.
9. Add tests (unit, integration, UI).
10. Performance tuning and security review.
11. Build verification and PR.

## 10 — Notes about non-invasive integration
- The overlay will be a Compose layer (Composable) over AsyncImage so original images are preserved; user can toggle original/translated/overlay.
- Use existing image-loading (Coil) to obtain Bitmaps where needed; avoid reloading large images multiple times.
- Keep default behavior unchanged when translation feature is disabled.

