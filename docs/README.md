# 📚 SRVLS Documentation Hub

**Spaced Repetition Vocabulary Learning Software (SRVLS) v2**

Chào mừng đến với trung tâm tài liệu cho dự án SRVLS! Đây là nơi quản lý tất cả các user stories, specifications, và documentation.

---

## 🗂️ **Cấu trúc Documentation**

```
docs/
├── 📁 user-stories/          # User Stories theo Sprint
│   ├── 📁 sprint-1/          # Sprint 1 MVP Stories
│   ├── 📁 sprint-2/          # Sprint 2 Stories (TBD)
│   └── 📁 backlog/           # Product Backlog
├── 📁 templates/             # Templates cho documentation
├── 📁 specifications/        # Technical Specifications
├── 📁 api/                   # API Documentation
└── 📁 guidelines/            # Development Guidelines
```

---

## 🎯 **Current Sprint: Sprint 1 - MVP**

### **📋 Sprint Overview**
- **Duration:** July 15 - July 29, 2025 (2 weeks)
- **Goal:** Implement core flashcard review with spaced repetition
- **Total Story Points:** 60
- **Team Velocity:** 30 points/week

### **📊 Sprint Progress**
| **Status** | **Count** | **Story Points** |
|------------|-----------|------------------|
| 📋 Todo | 5 | 60 |
| 🔄 In Progress | 0 | 0 |
| 👀 Review | 0 | 0 |
| ✅ Done | 0 | 0 |

### **📖 Sprint Documentation**
- [📝 Sprint 1 User Stories](./user-stories/sprint-1/README.md)
- [🔗 GitHub Issues](https://github.com/tiendainguyen/space-repitation/issues?q=is%3Aissue+label%3Asprint-1)

---

## 📝 **User Stories Quick Access**

### **Sprint 1 Stories:**
| **ID** | **Title** | **Points** | **Priority** | **Issues** |
|--------|-----------|------------|--------------|------------|
| **US-1** | [Load Flashcards for Review](./user-stories/sprint-1/README.md#user-story-1-load-flashcards-for-review) | 8 | High | [#3](../../issues/3), [#4](../../issues/4) |
| **US-2** | [Flip Flashcard and Rate Difficulty](./user-stories/sprint-1/README.md#user-story-2-flip-flashcard-and-rate-difficulty) | 13 | High | [#5](../../issues/5) |
| **US-3** | [Track Review Progress](./user-stories/sprint-1/README.md#user-story-3-track-review-progress-and-completion) | 5 | Medium | [#6](../../issues/6) |
| **US-4** | [Smart Batch Review Storage](./user-stories/sprint-1/README.md#user-story-4-save-review-results-via-smart-batch) | 21 | High | [#7](../../issues/7), [#8](../../issues/8) |
| **US-5** | [SM-2 Spaced Repetition Algorithm](./user-stories/sprint-1/README.md#user-story-5-update-nextreviewdate-using-spaced-repetition-algorithm) | 13 | Critical | [#9](../../issues/9) |

---

## 🔧 **Development Resources**

### **📋 Templates & Guidelines**
- [📝 User Story Template](./templates/user-story-template.md)
- [🔧 Issue Template](../../.github/ISSUE_TEMPLATE/)
- [📚 Pull Request Template](../../.github/PULL_REQUEST_TEMPLATE.md)

### **📊 Technical Documentation**
- [📘 Technical Specification v2](../technical_spec_v2.md)
- [📗 User Requirements Document](../urd_review_card_v1.md)
- [🏗️ Architecture Overview](./specifications/architecture.md) *(TBD)*
- [📖 API Documentation](./api/README.md) *(TBD)*

---

## 🚀 **Quick Start Guide**

### **1. Tạo User Story mới:**
1. Copy [User Story Template](./templates/user-story-template.md)
2. Điền thông tin story vào template
3. Tạo file mới trong `docs/user-stories/sprint-X/`
4. Tạo GitHub Issues tương ứng

### **2. Quản lý Sprint:**
1. Update file Sprint README với progress
2. Link các GitHub Issues với User Stories
3. Track story points và velocity

### **3. Documentation Workflow:**
```bash
# 1. Clone repository
git clone https://github.com/tiendainguyen/space-repitation.git

# 2. Create documentation branch
git checkout -b docs/sprint-2-stories

# 3. Add documentation
mkdir -p docs/user-stories/sprint-2
cp docs/templates/user-story-template.md docs/user-stories/sprint-2/us-01.md

# 4. Commit and push
git add docs/
git commit -m "docs: Add Sprint 2 user stories"
git push origin docs/sprint-2-stories

# 5. Create Pull Request
```

---

## 📈 **Project Tracking**

### **📊 Overall Progress**
- **Total Epics:** 1 (Review Flashcards)
- **Total User Stories:** 5 (Sprint 1)
- **Completed Stories:** 0
- **In Progress:** 0
- **Backlog:** TBD

### **🔄 Integration với GitHub**
- **Issues:** Mỗi technical task có GitHub Issue riêng
- **Projects:** Sử dụng GitHub Projects để track sprint
- **Milestones:** Sprint 1, Sprint 2, etc.
- **Labels:** `sprint-1`, `user-story-1`, `backend`, `frontend`, etc.

---

## 💡 **Best Practices**

### **📝 Documentation Guidelines:**
1. **Consistency:** Sử dụng templates cho tất cả user stories
2. **Traceability:** Link user stories với GitHub issues
3. **Version Control:** Commit documentation changes theo feature
4. **Review Process:** Documentation cũng cần code review

### **🔗 Linking Strategy:**
- User Story ↔ GitHub Issues
- Issues ↔ Pull Requests  
- Documentation ↔ Code
- Tests ↔ Acceptance Criteria

### **📊 Progress Tracking:**
- Cập nhật progress hàng ngày
- Review story points sau mỗi sprint
- Track velocity để planning sprint tiếp theo

---

## 🛠️ **Tools & Integrations**

### **📋 Documentation Tools:**
- **GitHub Markdown:** Primary documentation format
- **Mermaid Diagrams:** For flowcharts and diagrams
- **GitHub Projects:** Sprint tracking
- **GitHub Wiki:** *(Optional)* For larger documentation

### **🔄 Automation:**
- **GitHub Actions:** Auto-update documentation
- **Issue Templates:** Consistent issue creation
- **PR Templates:** Standard review process

---

## 📞 **Support & Contacts**

### **📧 Team Contacts:**
- **Product Owner:** TBD
- **Scrum Master:** TBD  
- **Tech Lead:** TBD
- **Documentation Lead:** TBD

### **📚 External Resources:**
- [GitHub Documentation](https://docs.github.com/)
- [Agile User Story Guide](https://www.atlassian.com/agile/project-management/user-stories)
- [Technical Writing Guidelines](https://developers.google.com/tech-writing)

---

## 📅 **Change Log**

| **Date** | **Version** | **Changes** | **Author** |
|----------|-------------|-------------|------------|
| 2025-07-15 | 1.0 | Initial documentation structure | Development Team |
| 2025-07-15 | 1.1 | Sprint 1 user stories added | Development Team |

---

**Last Updated:** July 15, 2025  
**Document Version:** 1.1  
**Maintained by:** Development Team

---

## 🎯 **Next Steps**

- [ ] Setup GitHub Projects board cho Sprint 1
- [ ] Tạo issue templates
- [ ] Setup GitHub Actions cho documentation automation
- [ ] Planning Sprint 2 user stories
- [ ] API documentation setup
