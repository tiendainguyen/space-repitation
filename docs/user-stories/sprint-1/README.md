# 📦 Sprint 1 – MVP: Spaced Repetition Flashcard Review

## 🎯 Epic: Review Vocabulary Flashcards

**Sprint Goal:** Implement core flashcard review functionality with spaced repetition algorithm

**Duration:** 2 weeks  
**Start Date:** July 15, 2025  
**End Date:** July 29, 2025  

---

## 📚 User Stories Overview

| **Story** | **Title** | **Story Points** | **Priority** | **Status** | **Issues** |
|-----------|-----------|------------------|--------------|------------|------------|
| **US-1** | Load Flashcards for Review | 8 | High | 📋 Todo | [#3](../../issues/3), [#4](../../issues/4) |
| **US-2** | Flip Flashcard and Rate Difficulty | 13 | High | 📋 Todo | [#5](../../issues/5) |
| **US-3** | Track Review Progress | 5 | Medium | 📋 Todo | [#6](../../issues/6) |
| **US-4** | Smart Batch Review Storage | 21 | High | 📋 Todo | [#7](../../issues/7), [#8](../../issues/8) |
| **US-5** | SM-2 Spaced Repetition Algorithm | 13 | Critical | 📋 Todo | [#9](../../issues/9) |

**Total Story Points:** 60  
**Estimated Velocity:** 30 points/week  

---

## ✅ User Story 1: Load Flashcards for Review

**As a** user,  
**I want to** start studying a deck,  
**so that** I can review only the flashcards I need to practice today.

### ✅ Acceptance Criteria:
- [x] When user selects a deck in review mode, a request is sent to the backend to fetch flashcards
- [x] Backend returns flashcards in 3 categories:
  - **new**: `createdDate == today`
  - **inReview**: `nextReviewDate == today OR nextReviewDate < today OR (nextReviewDate == null AND createdDate < today)`
  - **learned**: `nextReviewDate > today`
- [x] Frontend filters and only shows **inReview** flashcards in the study session

### 🔗 Related Issues:
- [#3 - Backend API: Load Flashcards for Review](../../issues/3)
- [#4 - Frontend Review Interface](../../issues/4)

### 📊 Story Points: 8
**Breakdown:**
- Backend API (5 pts)
- Frontend Integration (3 pts)

---

## ✅ User Story 2: Flip Flashcard and Rate Difficulty

**As a** user,  
**I want to** flip a flashcard and rate its difficulty,  
**so that** the system can adapt my learning schedule.

### ✅ Acceptance Criteria:
- [x] When the flashcard is flipped, show the back side with definition and pronunciation
- [x] Show 5 rating buttons: `Again (1)`, `Hard (2)`, `Good (3)`, `Easy (4)`, `Very Easy (5)`
- [x] Once rated, the flashcard disappears, and the next inReview flashcard appears

### 🔗 Related Issues:
- [#5 - Flashcard Flip Animation and Rating Interface](../../issues/5)

### 📊 Story Points: 13
**Breakdown:**
- Flip Animation (5 pts)
- Rating Interface (5 pts)
- Card Navigation (3 pts)

---

## ✅ User Story 3: Track Review Progress and Completion

**As a** user,  
**I want to** see my review progress,  
**so that** I feel motivated and know when I finish.

### ✅ Acceptance Criteria:
- [x] Show **learned overview** (the number of words learned)
- [x] Show **pending desk** overview (the number of words left to review)
- [x] When all inReview cards are rated, show "Well done" message or transition screen

### 🔗 Related Issues:
- [#6 - Review Progress Tracking and Completion Interface](../../issues/6)

### 📊 Story Points: 5
**Breakdown:**
- Progress UI (3 pts)
- Completion Modal (2 pts)

---

## ✅ User Story 4: Save Review Results via Smart Batch

**As a** system,  
**I want to** collect reviewed flashcards and batch-send them to the backend,  
**so that** user review results are stored efficiently.

### ✅ Acceptance Criteria:
- [x] Client stores review result (cardId, rating, timestamp) in localStorage until batch send
- [x] Smart batch is sent via HTTP to backend after:
  - Review session ends
  - or 5–10 cards are reviewed
  - or after 30 seconds of inactivity
- [x] Batch data includes flashcard ID, difficulty rating (1–5), and timestamp

### 🔗 Related Issues:
- [#7 - Frontend Local Storage and Smart Batch Sync](../../issues/7)
- [#8 - Backend Batch Review Processing](../../issues/8)

### 📊 Story Points: 21
**Breakdown:**
- Frontend Local Storage (8 pts)
- Smart Batch Logic (5 pts)
- Backend Batch API (8 pts)

---

## ✅ User Story 5: Update nextReviewDate using Spaced Repetition Algorithm

**As a** backend service,  
**I want to** compute the nextReviewDate for each flashcard,  
**so that** user can retain vocabulary efficiently over time.

### ✅ Acceptance Criteria:
- [x] Use SM-2 (Anki) algorithm to update:
  - **EF (Ease Factor)**
  - **interval (days to next review)**
  - **nextReviewDate**
- [x] Persist updated flashcard state in the database

### 🔗 Related Issues:
- [#9 - Core Algorithm: SM-2 Spaced Repetition Implementation](../../issues/9)

### 📊 Story Points: 13
**Breakdown:**
- SM-2 Algorithm Implementation (8 pts)
- Database Integration (3 pts)
- Testing & Validation (2 pts)

---

## 🔄 Sprint Workflow

### **Week 1 Focus:**
1. **Backend Foundation** (Issues #3, #9)
   - API endpoints for flashcard loading
   - SM-2 algorithm implementation
2. **Frontend Core** (Issues #4, #5)
   - Review interface
   - Flip animations and rating

### **Week 2 Focus:**
1. **Smart Features** (Issues #6, #7, #8)
   - Progress tracking
   - Local storage and batch sync
2. **Integration & Testing**
   - End-to-end testing
   - Performance optimization

---

## 📈 Definition of Done

For each user story to be considered complete:

- [x] All acceptance criteria are met
- [x] Code is reviewed and approved
- [x] Unit tests written and passing
- [x] Integration tests passing
- [x] Documentation updated
- [x] Feature deployed to staging environment
- [x] Product owner approval

---

## 🚨 Sprint Risks & Mitigation

| **Risk** | **Impact** | **Probability** | **Mitigation** |
|----------|------------|-----------------|----------------|
| SM-2 Algorithm complexity | High | Medium | Start early, extensive testing |
| Frontend-Backend integration | Medium | Low | Clear API contracts |
| Local storage limitations | Low | Low | Fallback to immediate sync |

---

## 📝 Sprint Notes

- **Sprint Planning:** July 14, 2025
- **Daily Standups:** 9:00 AM UTC
- **Sprint Review:** July 29, 2025
- **Sprint Retrospective:** July 30, 2025

---

**Last Updated:** July 15, 2025  
**Document Owner:** Product Team  
**Sprint Master:** TBD
