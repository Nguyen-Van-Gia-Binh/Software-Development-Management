# Tài Liệu Mô Tả Cấu Trúc Bổ Sung Hệ Thống Class Diagram
**Dự án**: Software Developer Management

Tài liệu này giải thích chi tiết cấu trúc các class, phương thức và **100% các mối quan hệ (Relationships)** giữa chúng trong mã nguồn để bạn có thể nắm được quy tắc và **tự vẽ các đường mũi tên liên kết** ứng với danh sách các Class rời rạc trong file Draw.io.

---

## 1. Danh sách các Class và Thuộc Tính bổ sung

*Lưu ý: Bạn chỉ cần vẽ lại các bảng từ file Draw.io, chúng đã có đủ các thuộc tính và phương thức.*

Đặc biệt lưu ý class **`Validator`** có các thuộc tính là hằng số tĩnh (`static final`), khi vẽ hãy gạch dưới các thuộc tính này để biểu thị tính `static`:
- `+ DEV_ID_REGEX : String`
- `+ FULL_NAME_REGEX : String`
- `+ SALARY_REGEX : String`
- `+ DURATION_REGEX : String`
- `+ NON_EMPTY_REGEX : String`
- `+ DATE_VALID : String`
- `+ PROJ_ID_REGEX : String`
- `+ DATE_TIME_FORMATTER : DateTimeFormatter`

---

## 2. Chi tiết Cách Tự Vẽ Mối Quan Hệ (Relationships)

Dưới đây là danh sách toàn bộ các đường liên kết bạn cần kéo giữa các class:

### A. Kế Thừa & Thực Thi (Inheritance / Implementation)
**Ký hiệu**: Mũi tên có nét liền (đối với lớp) hoặc nét đứt (đối với Interface), đầu mũi tên là **hình tam giác rỗng** (không tô màu).

1. **`DeveloperList` kế thừa `ArrayList<Developer>`**:
   - Kéo từ `DeveloperList` tới `ArrayList<Developer>`.
   - Nét liền, đầu mũi tên tam giác rỗng.
2. **`ProjectList` kế thừa `ArrayList<Project>`**:
   - Kéo từ `ProjectList` tới `ArrayList<Project>`.
   - Nét liền, đầu mũi tên tam giác rỗng.
3. **`DeveloperList` thực thi `Iservice<T>`**:
   - Kéo từ `DeveloperList` tới `Iservice<T>`.
   - **Nét đứt**, đầu mũi tên tam giác rỗng.
4. **`ProjectList` thực thi `Iservice<T>`**:
   - Kéo từ `ProjectList` tới `Iservice<T>`.
   - **Nét đứt**, đầu mũi tên tam giác rỗng.

---

### B. Quan Hệ Cấu Thành (Composition)
**Ký hiệu**: Mũi tên nét liền, ở class "Chứa" có **hình thoi đặc màu đen**. Thể hiện vòng đời của đối tượng thành phần phụ thuộc hoàn toàn vào đối tượng chứa nó.

1. **`Controller` cấu thành từ `DeveloperList`**: 
   - Đầu hình thoi màu đen gắn ở `Controller`. Mũi tên thẳng kéo đến `DeveloperList`. (Kèm số `1` ở 2 đầu).
2. **`Controller` cấu thành từ `ProjectList`**:
   - Đầu hình thoi màu đen gắn ở `Controller`. Mũi tên thẳng kéo đến `ProjectList`. (Kèm số `1` ở 2 đầu).

*(Giải thích: Trong hàm khởi tạo `Controller()`, nó đồng thời tạo ra `new DeveloperList()` và `new ProjectList()`. Nếu Controller biến mất, 2 list này cũng mất theo).*

---

### C. Quan Hệ Tập Hợp (Aggregation)
**Ký hiệu**: Mũi tên nét liền, ở class "Danh sách chứa" có **hình thoi rỗng** (không tô màu). Thể hiện một đối tượng bao gồm nhiều đối tượng khác, nhưng chúng có thể tồn tại độc lập.

1. **`DeveloperList` tập hợp nhiều `Developer`**:
   - Đầu hình thoi rỗng gắn ở `DeveloperList`. Mũi tên thẳng (kèm chỉ số `*` nghĩa là nhiều) kéo đến `Developer`.
2. **`ProjectList` tập hợp nhiều `Project`**:
   - Đầu hình thoi rỗng gắn ở `ProjectList`. Mũi tên thẳng (kèm chỉ số `*` nghĩa là nhiều) kéo đến `Project`.

---

### D. Quan Hệ Liên Kết Có Hướng (Directed Association)
**Ký hiệu**: Mũi tên nét liền, đầu mũi tên là nét trơn (hình chữ V `>`). Thể hiện một class có chứa tham chiếu (reference/biến) trỏ tới class kia.

1. **`Project` liên kết tới `Developer`**: 
   - Mũi tên kéo từ `Project` sang `Developer`. Trên đường mũi tên ghi tên thuộc tính: `- devId`
2. **`ProjectList` liên kết tới `DeveloperList`**: 
   - Mũi tên kéo từ `ProjectList` sang `DeveloperList`. Trên đường mũi tên ghi tên thuộc tính: `- developerList`

---

### E. Quan Hệ Phụ Thuộc (Dependency)
**Ký hiệu**: Mũi tên **nét đứt**, đầu mũi tên mở (hình chữ V `>`). Thể hiện class này có sử dụng class kia bên trong hàm (có thể là tham số truyền vào, hoặc gọi hàm tĩnh - static type).

1. **Từ `Main` đến `Menu`**:
   - Mũi tên nét đứt từ `Main` trỏ vào `Menu` (Vì Main gọi Menu để hiển thị).
2. **Từ `Menu` đến `Controller`**:
   - Mũi tên nét đứt từ `Menu` trỏ vào `Controller` (Menu điều phối các luồng của Controller).
3. **Từ các List tới các class Tiện ích (Utilities)**:
   - `DeveloperList` phụ thuộc vào `Inputter`, `Validator`, `FileUtils`. (Kéo 3 mũi tên nét đứt từ DeveloperList trỏ đến 3 class này).
   - `ProjectList` phụ thuộc vào `Inputter`, `Validator`, `FileUtils`. (Kéo 3 mũi tên nét đứt từ ProjectList trỏ đến 3 class này).
