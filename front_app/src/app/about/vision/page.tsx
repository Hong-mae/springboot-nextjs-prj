export default function Page() {
  async function handleSubmit(formData: FormData) {
    "use server";
    const name = formData.get("name") as string;
    return `안녕하세요, ${name}님!`;
  }

  return (
    <form action={handleSubmit}>
      <input type="text" name="name" required />
      <button type="submit">제출</button>

      {/* 서버에서 반환한 값이 자동으로 표시됨 */}
      <output>00</output>
    </form>
  );
}
