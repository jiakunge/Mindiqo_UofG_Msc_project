const postsMockData = [
    {
      id: 1,
      img: "https://placekitten.com/400/300",
      title: "The first post",
      abs: "This is a brief description of the first post. It talks about some interesting things.",
      createTime: new Date(2023, 5, 12).toISOString(),
      likeCount: 125,
      commentCount: 47,
      creator: {
        avatar: "https://placekitten.com/100/100",
        username: "JohnDoe"
      }
    },
    {
      id: 2,
      img: "https://placekitten.com/401/301",
      title: "A fascinating second post",
      abs: "The second post dives deeper into other interesting aspects. It's very engaging.",
      createTime: new Date(2023, 6, 15).toISOString(),
      likeCount: 210,
      commentCount: 68,
      creator: {
        avatar: "https://placekitten.com/101/101",
        username: "JaneDoe"
      }
    },
    {
      id: 3,
      img: "https://placekitten.com/402/302",
      title: "The third post is here!",
      abs: "Last but not least, the third post. It has its unique charm and insights.",
      createTime: new Date(2023, 7, 7).toISOString(),
      likeCount: 89,
      commentCount: 32,
      creator: {
        avatar: "https://placekitten.com/102/102",
        username: "MarryDoeandJohnDoeandJaneDoe"
      }
    }
  ];

  const PostViewMock = {
    post: {
        title: "Sample Post Title",
        createTime: "2023-08-12T14:30:00Z",
        creator: {
            username: "JohnDoe",
            avatar: "https://example.com/avatar.jpg"
        },
        likeCount: 23,
        commentCount: 5,
        tags: [
            { tagName: "Vue.js" },
            { tagName: "JavaScript" },
            { tagName: "Frontend" }
        ],
        content: "<p>This is the content of the post. You can insert any HTML here to simulate the content rendered by <code>v-html</code>.</p>"
    },
    comments: [
      {
        id: 1,
        user: {
            username: "Alice",
            avatar: "https://example.com/avatar/alice.jpg"
        },
        createTime: "2023-08-12T15:00:00Z",
        content: "Great post! I learned a lot."
    },
    {
        id: 2,
        user: {
            username: "Bob",
            avatar: "https://example.com/avatar/bob.jpg"
        },
        createTime: "2023-08-12T16:00:00Z",
        content: "Can you explain more about the third point?"
    },
    {
        id: 3,
        user: {
            username: "Charlie",
            avatar: "https://example.com/avatar/charlie.jpg"
        },
        createTime: "2023-08-12T17:00:00Z",
        content: "Thanks for sharing!"
    }
    ]
}
  
  export default postsMockData;
  export { PostViewMock };