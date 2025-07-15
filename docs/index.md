---
layout: default
title: SRVLS Documentation
---

# 📚 SRVLS Documentation

Welcome to the **Spaced Repetition Vocabulary Learning Software** documentation site!

## 🚀 Quick Navigation

<div class="nav-grid">
  <div class="nav-card">
    <h3>📝 User Stories</h3>
    <p>Sprint planning and user story management</p>
    <a href="user-stories/sprint-1/" class="btn">View Sprint 1</a>
  </div>
  
  <div class="nav-card">
    <h3>🔧 Development</h3>
    <p>Technical specifications and API docs</p>
    <a href="specifications/" class="btn">View Specs</a>
  </div>
  
  <div class="nav-card">
    <h3>📊 Progress</h3>
    <p>Sprint progress and project tracking</p>
    <a href="https://github.com/tiendainguyen/space-repitation/issues" class="btn">View Issues</a>
  </div>
</div>

## 📋 Current Sprint: Sprint 1 MVP

**Duration:** July 15 - July 29, 2025  
**Goal:** Implement core flashcard review with spaced repetition  
**Progress:** 0/60 story points completed

### 🎯 Sprint Stories
- [US-1: Load Flashcards for Review](user-stories/sprint-1/#user-story-1-load-flashcards-for-review) - 8 pts
- [US-2: Flip Flashcard and Rate Difficulty](user-stories/sprint-1/#user-story-2-flip-flashcard-and-rate-difficulty) - 13 pts  
- [US-3: Track Review Progress](user-stories/sprint-1/#user-story-3-track-review-progress-and-completion) - 5 pts
- [US-4: Smart Batch Review Storage](user-stories/sprint-1/#user-story-4-save-review-results-via-smart-batch) - 21 pts
- [US-5: SM-2 Spaced Repetition Algorithm](user-stories/sprint-1/#user-story-5-update-nextreviewdate-using-spaced-repetition-algorithm) - 13 pts

## 🔗 Quick Links

- [📖 Technical Specification](../technical_spec_v2.md)
- [📝 User Requirements](../urd_review_card_v1.md)
- [🔄 GitHub Issues](https://github.com/tiendainguyen/space-repitation/issues)
- [📊 Project Board](https://github.com/tiendainguyen/space-repitation/projects)

<style>
.nav-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1rem;
  margin: 2rem 0;
}

.nav-card {
  border: 1px solid #e1e4e8;
  border-radius: 6px;
  padding: 1rem;
  background: #f6f8fa;
}

.nav-card h3 {
  margin-top: 0;
  color: #0366d6;
}

.btn {
  display: inline-block;
  padding: 0.375rem 0.75rem;
  background-color: #0366d6;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-size: 0.875rem;
}

.btn:hover {
  background-color: #0256cc;
  color: white;
  text-decoration: none;
}
</style>
