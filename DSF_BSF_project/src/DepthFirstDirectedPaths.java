

public class DepthFirstDirectedPaths {
	
		private boolean[] marked;     // marked[v] = is there an s-v path?
			int edgeTo[];     // edgeTo[v] = last edge on s-v path
			int source;           // source vertex

			// depth first search from v
		public	void dfs(DirectedGraph graph, int vertex) {
			
				marked[vertex] = true;
				for (int aux : graph.getAdyacents()) {
					
					if (!marked[vertex]) {
						edgeTo[vertex] = vertex;
						dfs(graph, aux);
					}
				}
			}


	public		DepthFirstDirectedPaths(const GrafoDirigido& G, int s) : s(s) {
				edgeTo = new int[G.V()];
				marked = new bool[G.V()];
				for(int i = 0; i < G.V(); i++) marked[i] = false;
				dfs(G, s);
			}

			~DepthFirstDirectedPaths() {
				delete[] marked; marked = NULL;
				delete[] edgeTo; edgeTo = NULL;
			}

			// is there a path between s and v?
			bool hasPathTo(int v) const {
				return marked[v];
			}

			// return a path between s to v; null if no such path
			Path pathTo(int v) const {
				Path path;
				if (!hasPathTo(v)) return path;
				for (int x = v; x != s; x = edgeTo[x])
					path.Cons(x);
				path.Cons(s);
				return path;
			}
		};


		class BreadthFirstDirectedPaths {
		private:
			bool* marked;     // marked[v] = is there an s-v path?
			int* edgeTo;     // edgeTo[v] = last edge on s-v path
			int* distTo;     // distTo[v] = number of edges shortest s-v path
			//	int s;           // source vertex

			// BFS from single source
			void bfs(const GrafoDirigido& G, int s) {
				Cola<int> q;
				distTo[s] = 0;
				marked[s] = true;
				q.ponDetras(s);

				while (!q.esVacia()) {
					int v = q.primero(); q.quitaPrim();
					for (Iter it = G.adj(v).principio(); it != G.adj(v).final(); it.avanza()) {
						int w = it.elem();
						if (!marked[w]) {
							edgeTo[w] = v;
							distTo[w] = distTo[v] + 1;
							marked[w] = true;
							q.ponDetras(w);
						}
					}
				}
			}

			// BFS from multiple sources
			void bfs(const GrafoDirigido& G, Lista<int> sources) {
				Cola<int> q;
				for(Iter it = sources.principio(); it != sources.final(); it.avanza()) {
					int s = it.elem();
					distTo[s] = 0;
					marked[s] = true;
					q.ponDetras(s);
				}

				while (!q.esVacia()) {
					int v = q.primero(); q.quitaPrim();
					for (Iter it = G.adj(v).principio(); it != G.adj(v).final(); it.avanza()) {
						int w = it.elem();
						if (!marked[w]) {
							edgeTo[w] = v;
							distTo[w] = distTo[v] + 1;
							marked[w] = true;
							q.ponDetras(w);
						}
					}
				}
			}

		public:
			// single source
			BreadthFirstDirectedPaths(const GrafoDirigido& G, int s) {
				edgeTo = new int[G.V()];
				marked = new bool[G.V()];
				distTo = new int[G.V()];
				for(int i = 0; i < G.V(); i++) marked[i] = false;
				for(int v = 0; v < G.V(); v++) distTo[v] = numeric_limits<int>::infinity();
				bfs(G, s);
			}

			// multiple source
			BreadthFirstDirectedPaths(const GrafoDirigido& G, const Lista<int>& sources) {
				edgeTo = new int[G.V()];
				marked = new bool[G.V()];
				distTo = new int[G.V()];
				for(int i = 0; i < G.V(); i++) marked[i] = false;
				for(int v = 0; v < G.V(); v++) distTo[v] = numeric_limits<int>::infinity();
				bfs(G, sources);
			}

			~BreadthFirstDirectedPaths() {
				delete[] marked; marked = NULL;
				delete[] edgeTo; edgeTo = NULL;
				delete[] distTo; distTo = NULL;
			}

			// length of shortest path from s (or sources) to v
			int distance(int v) const {
				return distTo[v];
			}

			// is there a path between s and v?
			bool hasPathTo(int v) const {
				return marked[v];
			}

			// return a path between s and v; empty if no such path
			Path pathTo(int v) const {
				Path path;
				if (!hasPathTo(v)) return path;
				int x;
				for (x = v; distTo[x] != 0; x = edgeTo[x])
					path.Cons(x);
				path.Cons(x);
				return path;
			}

		};


		class DepthFirstOrder {
		private:
			bool* marked;           // marked[v] = has v been marked in dfs?
			int* pre;               // pre[v]    = preorder  number of v
			int* post;              // post[v]   = postorder number of v
			Lista<int> preorder;   // vertices in preorder
			Lista<int> postorder;  // vertices in postorder
			Lista<int> revPost;    // vertices in reverse postorder
			int preCounter;        // counter or preorder numbering
			int postCounter;       // counter for postorder numbering

			// run DFS in digraph G from vertex v and compute preorder/postorder
			void dfs(const GrafoDirigido& G, int v) {
				marked[v] = true;
				pre[v] = preCounter++;
				preorder.ponDr(v);
				for (Iter it = G.adj(v).principio(); it != G.adj(v).final(); it.avanza()) {
					int w = it.elem();
					if (!marked[w]) {
						dfs(G, w);
					}
				}
				postorder.ponDr(v);
				post[v] = postCounter++;
				revPost.Cons(v);
			}

		public:
			// depth-first search preorder and postorder in a digraph
			DepthFirstOrder(const GrafoDirigido& G) {
				pre    = new int[G.V()]; preCounter = 0;
				post   = new int[G.V()]; postCounter = 0;
				marked    = new bool[G.V()];
				for (int v = 0; v < G.V(); v++) marked[v] = false;
				for (int v = 0; v < G.V(); v++)
					if (!marked[v]) dfs(G, v);
			}

			int prenum(int v) const {
				return pre[v];
			}

			int postnum(int v) const {
				return post[v];
			}

			// return vertices in postorder as an Iterable
			const Lista<int>& postOrder() const {
				return postorder;
			}

			// return vertices in postorder as an Iterable
			const Lista<int>& preOrder() const {
				return preorder;
			}

			// return vertices in reverse postorder as an Iterable
			const Lista<int>& reversePost() const {
				return revPost;
			}

		};


		class DirectedCycle {
		private:
			bool* marked;        // marked[v] = has vertex v been marked?
			int* edgeTo;         // edgeTo[v] = previous vertex on path to v
			bool* onStack;       // onStack[v] = is vertex on the stack?
			Lista<int> ciclo;   // directed cycle (or null if no such cycle)
			bool hayciclo;

			// check that algorithm computes either the topological order or finds a directed cycle
			void dfs(const GrafoDirigido& G, int v) {
				onStack[v] = true;
				marked[v] = true;
				for (Iter it = G.adj(v).principio(); it != G.adj(v).final(); it.avanza()) {
					int w = it.elem();
					// short circuit if directed cycle found
					if (hayciclo) return;
					//found new vertex, so recur
					else if (!marked[w]) {
						edgeTo[w] = v;
						dfs(G, w);
					}
					// trace back directed cycle
					else if (onStack[w]) {
						hayciclo = true;
						for (int x = v; x != w; x = edgeTo[x]) {
							ciclo.Cons(x);
						}
						ciclo.Cons(w);
						ciclo.Cons(v);
					}
				}
				onStack[v] = false;
			}

		public:
			DirectedCycle(const GrafoDirigido& G) {
				hayciclo = false;
				marked  = new bool[G.V()];
				for (int v = 0; v < G.V(); v++) marked[v] = false;
				onStack = new bool[G.V()];
				for (int v = 0; v < G.V(); v++) onStack[v] = false;
				edgeTo  = new int[G.V()];
				for (int v = 0; v < G.V(); v++)
					if (!marked[v]) dfs(G, v);
			}

			bool hasCycle() const { return hayciclo; }
			const Lista<int>& cycle() const { return ciclo; }
		};


		

}
