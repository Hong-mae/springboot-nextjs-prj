interface Props {
  params: Promise<{ id: number }>;
}

type ArticleType = {
  resultCode: string;
  msg: string;
  data: {
    article: {
      id: string;
      subject: string;
      content: string;
      createdAt: string;
      modifiedAt: string;
    };
  };
};

const getArticle = async (id: number): Promise<ArticleType> => {
  const article = await fetch(
    `http://localhost:8090/api/v1/articles/${id}`
  ).then((data) => data.json());

  return article;
};

export default async function ArticleDetail({ params }: Props) {
  const { id } = await params;
  const {
    data: {
      article: { subject, content, createdAt, modifiedAt },
    },
  } = await getArticle(id);
  return (
    <>
      <h1>게시판 상세 {id}번</h1>
      <div>{subject}</div>
      <div>{content}</div>
      <div>{createdAt}</div>
      <div>{modifiedAt}</div>
    </>
  );
}
